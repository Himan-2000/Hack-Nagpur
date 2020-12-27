const express = require('express')
const router = express.Router()
const { check, validationResult } = require('express-validator');
const bcrypt = require('bcryptjs');
const config = require('config');
const jwt = require('jsonwebtoken')
const auth = require('../middleware/auth');
const Report = require('../models/Report');
const Doctor = require('../models/Doctor');
const User = require('../models/User');
const Prescription = require('../models/Prescription')

//Register a Doctor

router.post('/register', [
    check('name', 'Please enter a name').not().isEmpty(),
    check('email', 'Enter a valid email address').isEmail(),
    check('password', 'Please enter a valid password').isLength({ min: 6 })
], async (req, res) => {
    const errors = validationResult(req)
    if (!errors.isEmpty()) {
        return res.status(400).send({
            errors: errors.array()
        })
    }

    const { name, email, password, Specialization } = req.body;

    try {
        let doctor = await Doctor.findOne({ email });

        if (doctor) {
            return res.status(400).json({
                msg: 'Doctor already exists'
            })
        }

        doctor = new Doctor({ name, email, password, Specialization });

        const salt = await bcrypt.genSalt(10);

        doctor.password = await bcrypt.hash(password, salt);

        await doctor.save();

        const payload = {
            user: {
                id: doctor.id
            }
        }

        jwt.sign(payload, config.get('jwtSecret'), {
            expiresIn: 360000
        }, (err, token) => {
            if (err) throw err;
            res.json({ token, doctor })
        })
    }
    catch (error) {
        console.log(error.message);
        res.status(500).send('Server Error')
    }
})


//Login the Doctor

router.post('/login', [
    check('email', 'Please enter a valid email id').isEmail(),
    check('password', 'Password is required').exists()
], async (req, res) => {
    const errors = validationResult(req);
    if (!errors.isEmpty()) {
        return res.status(400).send({
            errors: errors.array()
        })
    }
    const { email, password } = req.body;

    try {
        let doctor = await Doctor.findOne({ email });

        if (!doctor) {
            return res.status(400).json({
                msg: 'User npot found'
            })
        }

        const isMatch = await bcrypt.compare(password, doctor.password);

        if (!isMatch) {
            return res.status(400).json({
                msg: 'Incorrect password entered'
            })
        }

        const payload = {
            user: {
                id: doctor.id
            }
        }

        jwt.sign(payload, config.get('jwtSecret'), {
            expiresIn: 360000
        }, (err, token) => {
            if (err) throw err;
            res.json({ token, doctor })
        })

    } catch (error) {
        console.log(error.message);
        res.status(500).json({
            msg: 'Server Error'
        })

    }
})

//Get qr scanned users details on the desktop

router.get('/userDetails', auth, async (req, res) => {

    try {
        console.log(req.decoded.user.id)
        const doctor = await Doctor.findById(req.decoded.user.id);
        if (!doctor) {
            res.status(500).send({
                msg: 'Unauthorised to this facility'
            })
        }

        const user = await User.findOne({ currentDoctor: req.decoded.user.id });
        console.log(user)

        const report = await Report.find({ patient: user._id });
        console.log(report)
        if (!report || !user) {
            res.status(404).send({
                msg: 'Not found 404...'
            })
        }
        res.status(200).send({ report, userId: user._id, user })
    } catch (error) {
        console.log(error.message);
        res.status(500).json({
            msg: 'Server Error'
        })
    }

})

//Doctor writing prescription for the patient and saving it inside that patients db
router.post('/prescription', auth, async (req, res) => {

    try {
        const { prescription, userId } = req.body;

        const prescriptions = new Prescription({
            title: prescription.title,
            details: prescription.details,
            patient: userId,
            doctor: req.decoded.user.id
        })
        await prescriptions.save();

        res.status(200).send(prescriptions)
    } catch (error) {
        console.log(error.message);
        res.status(500).json({
            msg: 'Server Error'
        })
    }
})


//PHARMACY TO GET THE PRESCRIPTION USING CONTACT NUMBER
router.get('/:contact', async (req, res) => {
    try {
        const user = await User.findOne({ contact: req.params.contact });
        if (!user) {
            return res.status(404).send({ msg: 'User not found' })
        }
        const prescription = await Prescription.find({ patient: user._id });
        if (!prescription) {
            return res.status(404).send({ msg: "No prescriptions found" });
        }

        res.status(200).send(prescription);

    } catch (error) {
        console.log(error.message);
        res.status(500).json({
            msg: 'Server Error'
        })
    }
})

//To get the doctors details
router.get('/doc/getDoctor', auth, async (req, res) => {
    try {
        const user = req.decoded.user.id;
        const doctor = await Doctor.findById(req.decoded.user.id);

        console.log(doctor)
        if (!doctor) {
            return res.status(404).send({
                msg: 'Doctor not found in db'
            })
        }

        res.status(200).send(doctor)
    } catch (error) {
        console.log(error.message);
        res.status(500).json({
            msg: 'Server Error'
        })
    }
})


module.exports = router
