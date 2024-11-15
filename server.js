// Import required modules
const express = require('express');
const https = require('https');
const fs = require('fs');
const WebSocket = require('ws');
const nodemailer = require('nodemailer');
const crypto = require('crypto-js');
const sqlite3 = require('sqlite3').verbose();
const { AES } = require('crypto-js');

// Load TLS certificates
const options = {
  key: fs.readFileSync('server.key'),
  cert: fs.readFileSync('server.cert')
};

// Initialize Express app and WebSocket server
const app = express();
const server = https.createServer(options, app);
const wss = new WebSocket.Server({ server });

// Database setup
const db = new sqlite3.Database(':memory:');
db.serialize(() => {
  db.run("CREATE TABLE appointments (id INTEGER PRIMARY KEY, date TEXT, type TEXT)");
  db.run("CREATE TABLE products (id INTEGER PRIMARY KEY, name TEXT, price REAL, stock INTEGER)");
});

// SMTP transporter
const transporter = nodemailer.createTransport({
  service: 'gmail',
  auth: { user: 'youremail@gmail.com', pass: 'yourpassword' }
});

// Helper functions
function sendOTP(email) {
  const otp = Math.floor(100000 + Math.random() * 900000).toString();
  const encryptedOTP = AES.encrypt(otp, 'secret key 123').toString();
  transporter.sendMail({
    to: email,
    subject: 'Your OTP Code',
    text: `Your OTP is ${otp}`
  });
  return encryptedOTP;
}

// Middleware and static files
app.use(express.json());
app.use(express.static('public'));

// Appointment scheduling
app.post('/book-appointment', (req, res) => {
  const { date, type, email } = req.body;
  const dayOfWeek = new Date(date).getDay();
  if (dayOfWeek === 5 || dayOfWeek === 6 || dayOfWeek === 0) { // Fri, Sat, Sun
    db.run("INSERT INTO appointments (date, type) VALUES (?, ?)", [date, type], function(err) {
      if (err) return res.status(500).json({ error: 'Database error' });
      const otp = sendOTP(email);
      res.json({ message: 'Appointment booked!', otp: otp });
    });
  } else {
    res.status(400).json({ error: 'Appointments are only available on Fri, Sat, Sun' });
  }
});

// Product catalog
app.get('/products', (req, res) => {
  db.all("SELECT * FROM products", [], (err, rows) => {
    if (err) return res.status(500).json({ error: 'Database error' });
    res.json(rows);
  });
});

// WebSocket connection for real-time updates
wss.on('connection', ws => {
  ws.on('message', message => {
    const data = JSON.parse(message);
    if (data.type === 'product_update') {
      db.get("SELECT * FROM products WHERE id = ?", [data.id], (err, row) => {
        if (row) ws.send(JSON.stringify({ type: 'stock_update', product: row }));
      });
    }
  });
});

// Start the server
server.listen(3000, () => console.log('Server started on https://localhost:3000'));
