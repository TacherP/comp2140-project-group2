const express = require('express');
const path = require('path');
const app = express();
const PORT = 3000;

app.use(express.json());

// Serve the HTML file
app.get('/', (req, res) => {
  res.sendFile(path.join(__dirname, 'adminIndex.html'));
});

// Mock API for generating OTP
app.post('/api/admin/generate-otp', (req, res) => {
  const email = req.body.email;
  console.log(`OTP sent to ${email}`);
  res.json({ success: true });
});

// Mock API for verifying OTP
app.post('/api/admin/verify-otp', (req, res) => {
  const { email, otp } = req.body;
  console.log(`Verifying OTP for ${email}: ${otp}`);
  res.json({ success: true });
});

app.listen(PORT, () => {
  console.log(`Server running on http://localhost:${PORT}`);
});
