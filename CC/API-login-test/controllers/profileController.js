const bcrypt = require('bcrypt');
const { db } = require('../db');

// Fungsi untuk mendapatkan profil pengguna
async function getProfile(req, res) {
  const userId = req.user.userId; // Ambil userId dari token JWT

  // Ambil data pengguna berdasarkan userId
  const [rows] = await db.query('SELECT id, name, email FROM users WHERE id = ?', [userId]);

  if (rows.length === 0) {
    return res.status(404).json({ error: true, message: 'User not found' });
  }

  const user = rows[0];
  res.json({
    error: false,
    user: {
      id: user.id,
      name: user.name,
      email: user.email
    }
  });
}

// Fungsi untuk mengubah profil pengguna
async function updateProfile(req, res) {
  const userId = req.user.userId; // Ambil userId dari token JWT
  const { name, email, password } = req.body;

  // Cek apakah email sudah digunakan oleh pengguna lain
  const [existingUser] = await db.query('SELECT * FROM users WHERE email = ? AND id != ?', [email, userId]);

  if (existingUser.length > 0) {
    return res.status(400).json({ error: true, message: 'Email sudah digunakan' });
  }

  // Jika password diubah, hash password terlebih dahulu
  let hashedPassword = null;
  if (password) {
    hashedPassword = await bcrypt.hash(password, 10);
  }

  // Update data pengguna
  await db.query('UPDATE users SET name = ?, email = ?, password = ? WHERE id = ?', [
    name || null,
    email || null,
    hashedPassword || null,
    userId
  ]);

  res.json({ error: false, message: 'Profile updated successfully' });
}

module.exports = { getProfile, updateProfile };
