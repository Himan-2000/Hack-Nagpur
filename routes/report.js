const express = require('express')
const router = express.Router()
const User = require('../models/User');
const Report = require('../models/Report')

router.post('/', async (req, res) => {
    try {
        const { title, file, name } = req.body;
        const user = await User.findOne({ name });
        if (!user) {
            res.status(404).send({
                msg: 'User not found in the db...'
            })
        }
        const report = new Report({
            title, file,
            patient: user._id
        })

        await report.save()

        res.status(200).send(report)
    } catch (error) {
        console.log(error.message);
        res.status(500).json({
            msg: 'Server Error'
        })
    }

})


module.exports = router;