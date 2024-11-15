//Class for hosting Admin UI Component

const express = require('express');
const path = require('path');
const nodemailer = require('nodemailer');
const crypto = require('crypto'); // For generating secure OTPs
const app = express();
const PORT = 3000;

app.use(express.json());

// Temporary in-memory storage for OTPs
const otpStore = new Map(); // Stores OTPs as `email -> { otp, expiresAt }`

// Email Transporter Configuration
const transporter = nodemailer.createTransport({
  service: 'Gmail', // Change to 'Yahoo' or 'Outlook365' if needed
  auth: {
    user: process.env.EMAIL, // Sender's email
    pass: process.env.EMAIL_PASSWORD, // App password or email password
  },
});

// Serve the HTML file
app.get('/', (req, res) => {
  res.sendFile(path.join(__dirname, 'adminIndex.html'));
});

// API for generating OTP
app.post('/api/admin/generate-otp', async (req, res) => {
  const email = req.body.email;

  // Validate the email format
  if (!/^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(email)) {
    return res.status(400).json({ success: false, message: 'Invalid email address' });
  }

  // Generate a 6-digit OTP and set its expiry time (5 minutes)
  const otp = crypto.randomInt(100000, 999999).toString();
  const expiresAt = Date.now() + 5 * 60 * 1000; // 5 minutes in the future

  // Store the OTP
  otpStore.set(email, { otp, expiresAt });

  // Send the OTP via email
  try {
    await transporter.sendMail({
      from: process.env.EMAIL, // Sender's email
      to: email, // Recipient's email
      subject: 'Your Admin Login OTP',
      text: `Your OTP is ${otp}. It is valid for 5 minutes.`,
    });

    console.log(`OTP sent to ${email}: ${otp}`);
    res.json({ success: true, message: 'OTP sent successfully' });
  } catch (error) {
    console.error(`Failed to send OTP to ${email}:`, error);
    res.status(500).json({ success: false, message: 'Failed to send OTP' });
  }
});

// API for verifying OTP
app.post('/api/admin/verify-otp', (req, res) => {
  const { email, otp } = req.body;

  // Check if the OTP exists and is valid
  const storedOtpData = otpStore.get(email);

  if (!storedOtpData) {
    return res.status(400).json({ success: false, message: 'OTP not found. Please request a new OTP.' });
  }

  const { otp: storedOtp, expiresAt } = storedOtpData;

  if (Date.now() > expiresAt) {
    otpStore.delete(email);
    return res.status(400).json({ success: false, message: 'OTP has expired. Please request a new OTP.' });
  }

  if (otp !== storedOtp) {
    return res.status(400).json({ success: false, message: 'Invalid OTP. Please try again.' });
  }

  // OTP is valid
  otpStore.delete(email); // Clear OTP after successful validation
  console.log(`OTP verified for ${email}`);
  res.json({ success: true, message: 'OTP verified successfully' });
});

app.listen(PORT, () => {
  console.log(`Server running on http://localhost:${PORT}`);
});

