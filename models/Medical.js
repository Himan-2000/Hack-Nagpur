const mongoose = require('mongoose');
const Prescription = require('./Prescription');

const MedicalSchema = mongoose.Schema({
    name: String,
    email: String,
    password: String,
    prescriptions: [{
        type: mongoose.Schema.Types.ObjectId,
        ref: Prescription
    }],
    date: {
        type: Date,
        default: Date.now
    }

});

module.exports = mongoose.model('medical', MedicalSchema)