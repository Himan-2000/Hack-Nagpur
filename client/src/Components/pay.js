import React from 'react';
import axios from 'axios';
const Pay = () => {
  const payment = async () => {
    var config = {
      method: 'post',
      url: 'localhost:5000/api/payment/payment_order',
      headers: {},
    };

    const res = await axios(config);
    const order = res.data;
    console.log(order);
    const options = {
      key: 'rzp_test_RJJUKEC7ow8MZc', // Enter the Key ID generated from the Dashboard
      amount: order.data.amount, // Amount is in currency subunits. Default currency is INR. Hence, 50000 refers to 50000 paise
      currency: 'INR',
      name: 'Carte',

      order_id: order.data.id, //This is a sample Order ID. Pass the `id` obtained in the response of Step 1
      handler: async function (res) {
        var raw = JSON.stringify({
          Payment_id: res.razorpay_payment_id,
          Order_id: res.razorpay_order_id,
          Payment_Signature: res.razorpay_signature,
        });

        console.log(res.razorpay_payment_id);
        console.log(res.razorpay_order_id);
        console.log(res.razorpay_signature);
      },

      theme: {
        color: '#0F0F0F',
      },
    };

    var rzp1 = new window.Razorpay(options);
    rzp1.on('payment.failed', function (response3) {
      console.log(response3.error);
      alert(`There is some error with payment!`);
    });
    rzp1.open();
  };
  return (
    <div>
      <h1>Hello</h1>
      <button onClick={payment}>Pay Here !</button>
    </div>
  );
};

export default Pay;
