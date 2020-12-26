const mongoose = require('mongoose');
const User = require('./User');
const Doctor = require('./Doctor');

const PrescriptionSchema = mongoose.Schema({
    doctor: {
        type: mongoose.Schema.Types.ObjectId,
        ref: Doctor
    },
    title: String,
    details: String,
    patient: {
        type: mongoose.Schema.Types.ObjectId,
        ref: User
    },
    date: {
        type: Date,
        default: Date.now
    }
});

module.exports = mongoose.model('prescription', PrescriptionSchema)
