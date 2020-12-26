const mongoose = require('mongoose');
const Doctor = require('./Doctor');

const UserSchema = mongoose.Schema({
    name: {
        type: String,
        required: true
    },
    age: {
        type: String,
        required: true
    },
    email: {
        type: String,
        required: true,
        unique: true
    },
    password: {
        type: String,
        required: true
    },
    contact: String,
    emergencyContact: [String],
    doctors: [{
        type: mongoose.Schema.Types.ObjectId,
        ref: Doctor
    }],
    currentDoctor: {
        type: mongoose.Schema.Types.ObjectId,
        ref: Doctor
    },
    date: {
        type: Date,
        default: Date.now
    },
    roles: [String],
    diary: {
        type: Boolean,
        default: false
    }
});

module.exports = mongoose.model('user', UserSchema)
