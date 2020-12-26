const mongoose = require('mongoose');
const User = require('./User');

const DiarySchema = mongoose.Schema({
    text: String,
    user: {
        type: mongoose.Schema.Types.ObjectId,
        ref: User
    },
    scores: {
        neg: String,
        neu: String,
        pos: String,
        compound: String
    },
    Summary: String,
    ent_list: [String],
    date: {
        type: Date,
        default: Date.now
    }
})

module.exports = mongoose.model('diary', DiarySchema)