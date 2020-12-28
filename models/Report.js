const mongoose = require('mongoose');
const User = require('./User');

const ReportSchema = mongoose.Schema({

    title: String,
    file: String,
    patient: {
        type: mongoose.Schema.Types.ObjectId,
        ref: User
    },
    date: {
        type: Date,
        default: Date.now
    }
});

module.exports = mongoose.model('report', ReportSchema)