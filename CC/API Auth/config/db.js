const mysql = require('mysql2/promise');
const dotenv = require('dotenv');

// Memuat variabel environment dari file .env
dotenv.config();

// Membuat koneksi pool ke Cloud SQL
const pool = mysql.createPool({
  host: process.env.DB_HOST,
  user: process.env.DB_USER,
  password: process.env.DB_PASSWORD,
  database: process.env.DB_NAME,
});

// Membuat koneksi pool ke Lokal
// const pool = mysql.createPool({
//   host: 'localhost',
//   user: 'root',
//   password: '',
//   database: 'glucoguide',
// });
module.exports = { db: pool };
