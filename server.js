const express = require('express');
const connectDB = require('./config/db');
const cors = require('cors');
const app = express();

connectDB();

app.use(express.json({ extended: false }));
app.use(cors())

app.use(function (req, res, next) {
    res.header("Access-Control-Allow-Origin", "*");
    res.header("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept");
    next();
});
app.get('/', (req, res) => {
    res.send({ msg: "Hello" })
})


const PORT = process.env.PORT || 5000;

app.use('/api/user', require('./routes/users'));
app.use('/api/doctor', require('./routes/doctor'));
app.use('/api/report', require('./routes/report'));
app.use('/api/diary', require('./routes/diary'));
app.use('/api/spotify', require('./routes/spotify'));
app.use('/api/pay', require('./routes/pay'))

app.listen(PORT, () => {
    console.log(`Listening to post ${PORT}`)
})