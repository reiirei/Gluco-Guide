const mysql = require('mysql2/promise');

// Create a MySQL connection pool
const pool = mysql.createPool({
    host: 'localhost',
    user: 'root',
    password: '',
    database: 'auth_system',
});

// async function fetchUsers() {
//     try {
//       const [rows, fields] = await pool.query('SELECT * FROM users');
//       console.log(rows);
//     } catch (err) {
//       console.error(err);
//     }
//   }
  
// fetchUsers();

module.exports = { db: pool };
