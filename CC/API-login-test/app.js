const express = require('express');
const dotenv = require('dotenv');
const cors = require('cors');
const { db } = require('./db');
const authRoutes = require('./routes/authRoutes'); // Import authRoutes

dotenv.config();

const app = express();

// Middleware
app.use(express.json()); // Parse JSON bodies
app.use(cors()); // Enable CORS for all origins

// Routes
app.use('/api', authRoutes); // Gunakan /api sebagai prefix

// Start the server
const port = process.env.PORT || 3000;
app.listen(port, () => {
  console.log(`Server running on port ${port}`);
});
