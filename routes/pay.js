const express = require('express');
const router = express.Router();
var axios = require('axios');
const Razorpay = require('razorpay');
router.post('/payment_order', async (req, res, next) => {
  try {
    var instance = new Razorpay({
      key_id: 'rzp_test_RJJUKEC7ow8MZc',
      key_secret: 'oXbOudRE8WnlbUtz0wek0CVW',
    });
    var options = {
      amount: 15000, // amount in the smallest currency unit
      currency: 'INR',
      receipt: 'order_rcptid_11',
    };
    instance.orders.create(options, function (err, order) {
      console.log(order);
      return res.status(200).json({
        success: true,
        data: order,
      });
    });
  } catch (error) {
    return res.status(500).json({
      success: false,
      message: 'Something went Wrong',
    });
  }
});

module.exports = router;
