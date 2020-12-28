const mongoose = require('mongoose');

const DoctorSchema = mongoose.Schema({
    name: String,
    email: String,
    password: String,
    Specialization: [String],
    date: {
        type: Date,
        default: Date.now
    },
    clientData: {
        startDate: String,
        endDate: String,
        userId: String
    },
    roles: [String]
});

module.exports = mongoose.model('doctor', DoctorSchema)
