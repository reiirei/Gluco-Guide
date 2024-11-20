const express = require('express');
const router = express.Router();
const GlucoController = require('../controllers/glucoController');

// Rute untuk klasifikasi 
router.post('/classify', GlucoController.classifyRisk);

// Rute untuk mendapatkan sumber web
router.get('/resources', GlucoController.getResources);

module.exports = router;
