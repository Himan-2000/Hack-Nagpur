const express = require('express')
const router = express.Router()
const User = require('../models/User')
const { check, validationResult } = require('express-validator');
const config = require('config');
const jwt = require('jsonwebtoken')
const auth = require('../middleware/auth');
const Report = require('../models/Report');
const Doctor = require('../models/Doctor');
const Prescription = require('../models/Prescription')

//Register a User

router.post('/register', [
    check('name', 'Please enter a name').not().isEmpty(),
    check('email', 'Enter a valid email address').isEmail()
], async (req, res) => {
    const errors = validationResult(req)
    if (!errors.isEmpty()) {
        return res.status(400).send({
            errors: errors.array()
        })
    }

    const { name, age, email, contact, password, emergencyContact } = req.body;

    try {
        let user = await User.findOne({ email });

        if (user) {
            return res.status(400).json({
                msg: 'User already exists'
            })
        }

        user = new User({ name, age, email, contact, emergencyContact, password });

        await user.save();

        const payload = {
            user: {
                id: user.id
            }
        }

        jwt.sign(payload, config.get('jwtSecret'), {
            expiresIn: 360000
        }, (err, token) => {
            if (err) throw err;
            res.json({ token, user })
        })
    }
    catch (error) {
        console.log(error.message);
        res.status(500).send('Server Error')
    }
})

//User adding its own details(eg.reports)

router.post('/userReports', async (req, res) => {
    const { title, file, contact } = req.body;
    const report = {
        title, file
    }
    try {
        const user = await User.findOne({ contact })
        report.patient = user._id;
        const reports = await Report(report).save();
        console.log(reports)
        res.status(200).send(reports)
    } catch (error) {
        console.log(error.message);
        res.status(500).send('Server Error')
    }
})


//Authenticating the user after the QR SCAN
router.post('/qrauth', async (req, res) => {

    try {
        const { doctorEmail, contact } = req.body;
        const users = await User.findOne({ contact });
        if (!users) {
            return res.status(404).send({
                msg: 'User not found in the db...'
            })
        }
        const doctors = await Doctor.findOne({ email: doctorEmail });
        await User.findByIdAndUpdate(
            users._id,
            { $set: { currentDoctor: doctors._id } },
            { new: true })
        const updatedUser = await User.findById(users._id)
        if (!doctors) {
            return res.status(404).send({
                msg: 'Doctor not found in the db... '
            })
        }
        console.log(updatedUser);
        res.status(200).send({ updatedUser })

    } catch (error) {
        console.log("hello", error.message);
        res.status(500).send('Server Error')
    }
})

//Login the user
router.post('/login', [
    check('email', 'Please enter a valid email id').isEmail()
], async (req, res) => {
    const errors = validationResult(req);
    if (!errors.isEmpty()) {
        return res.status(400).send({
            errors: errors.array()
        })
    }
    const { email, password } = req.body;

    try {
        let user = await User.findOne({ email });

        if (!user) {
            return res.status(400).json({
                msg: 'User npot found'
            })
        }

        if (password !== user.password) {
            return res.status(500).send({
                msg: "Invalid password"
            })
        }

        const payload = {
            user: {
                id: user.id
            }
        }

        jwt.sign(payload, config.get('jwtSecret'), {
            expiresIn: 360000
        }, (err, token) => {
            if (err) throw err;
            res.json({ token, user })
        })

    } catch (error) {
        console.log(error.message);
        res.status(500).json({
            msg: 'Server Error'
        })

    }
})


//For User to view his/her own detail

router.get('/:number', async (req, res) => {
    try {
        const user = await User.findOne({ contact: req.params.number });
        console.log(user)
        if (!user) {
            return res.status(404).send({
                msg: 'User not found in the db ....'
            })
        }
        const userReports = await Report.find({ patient: user._id });

        const userPrescriptions = await Prescription.find({ patient: user._id }).populate('doctor');

        if (!userReports && !userPrescriptions) {
            return res.status(400).send({
                msg: 'No previous reports and prescriptions available'
            })
        }

        const noOfReports = userReports.length
        const noOfPrescriptions = userPrescriptions.length



        res.status(201).send({ userPrescriptions, userReports, user, noOfReports, noOfPrescriptions });

    } catch (error) {
        console.log(error.message);
        res.status(500).json({
            msg: 'Server Error'
        })
    }
})

//Check the user in db
router.post('/findUser', async (req, res) => {
    try {
        const { contact } = req.body;
        const user = await User.findOne({ contact });
        if (!user) {
            res.status(404).send({ msg: 'User Not found', found: false })
        }
        res.status(200).send({ msg: 'User found', found: true, user })

    } catch (error) {
        console.log(error.message);
        res.status(500).json({
            msg: 'Server Error'
        })
    }
})

//Remove current doctor 
//after doc is done diagnosing the doctor can remove the users data
//onclick of remove user data

router.post('/removeCurrentDoctor', async (req, res) => {

    try {
        const { userId, doctorId } = req.body;

        const user = await User.findById(userId);
        if (!user) {
            return res.status(404).send({ msg: 'User not found...' });
        }

        await User.findByIdAndUpdate(
            userId,
            { $set: { currentDoctor: null } },
            { new: true })

        const updatedUser = await User.findById(userId);
        res.status(200).send(updatedUser)

    } catch (error) {
        console.log(error.message);
        res.status(500).json({
            msg: 'Server Error'
        })
    }

})


module.exports = router;
