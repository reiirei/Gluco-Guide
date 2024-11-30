const jwt = require('jsonwebtoken');
const bcrypt = require('bcrypt');
const { db } = require('../db');

// Fungsi untuk register pengguna
async function registerUser(req, res) {
  const { name, email, password } = req.body;

  // Cek apakah email sudah ada
  const [rows] = await db.query('SELECT * FROM users WHERE email = ?', [email]);
  if (rows.length > 0) {
    return res.status(400).json({ error: true, message: 'Email sudah terdaftar' });
  }

  // Hash password sebelum menyimpannya
  const hashedPassword = await bcrypt.hash(password, 10);

  // Simpan user baru ke database
  await db.query('INSERT INTO users (name, email, password) VALUES (?, ?, ?)', [name, email, hashedPassword]);

  res.json({ error: false, message: 'User Created' });
}


// Fungsi untuk login pengguna
async function loginUser(req, res) {
  const { email, password } = req.body;

  // Cari user berdasarkan email
  const [rows] = await db.query('SELECT * FROM users WHERE email = ?', [email]);
  if (rows.length === 0) {
    return res.status(400).json({ error: true, message: 'Email atau password salah' });
  }

  const user = rows[0];

  // Bandingkan password dengan yang ada di database
  const isPasswordValid = await bcrypt.compare(password, user.password);
  if (!isPasswordValid) {
    return res.status(400).json({ error: true, message: 'Email atau password salah' });
  }

  // Buat JWT token
  const token = jwt.sign({ userId: user.id, name: user.name }, process.env.JWT_SECRET, { expiresIn: '1h' });

  res.json({
    error: false,
    message: 'success',
    loginResult: {
      userId: user.id,
      name: user.name,
      token,
    },
  });
}


module.exports = { registerUser, loginUser };
