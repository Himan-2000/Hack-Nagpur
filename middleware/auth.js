const jwt = require('jsonwebtoken');
const config = require('config');
const User = require('../models/User');
const Doctor = require('../models/Doctor');


module.exports = async (req, res, next) => {
    //Get the token from the header

    const token = req.header('x-auth-token');

    //Check if not token

    if (!token) {
        return res.status(401).json({ msg: 'No token ,authorization denied' });
    }

    const decoded = jwt.verify(token, config.get('jwtSecret'))
    const user = await User.findById(decoded.user.id)
    req.decoded = decoded
    res.locals.user = user
    next();

    // } catch (err) {
    //     res.status(401).json({ msg: 'Token is not valid' })
    // }
}
