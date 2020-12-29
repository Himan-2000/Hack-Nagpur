const express = require('express');
const router = express.Router();
var axios = require('axios');
var qs = require('qs');

router.post('/playlist', async (req, res, next) => {
  try {
    var data = JSON.stringify({ query: req.body.message });
    var config = {
      method: 'post',
      url: 'https://healthcare-sentiment.herokuapp.com/fetch',
      headers: {
        'Content-Type': 'application/json',
      },
      data: data,
    };

    const sentiment_data = await axios(config);
    console.log(sentiment_data.data);
    const score = sentiment_data.data.scores.compound;
    var data = qs.stringify({
      grant_type: 'client_credentials',
    });

    var config = {
      method: 'post',
      url: 'https://accounts.spotify.com/api/token',
      headers: {
        Authorization:
          'Basic ZTExNjRhNzgwYWYwNGZkYWI1YzU0MGU5ZmMwNTFmMDk6NGUwNGQ5Zjg3ZmFiNGIxMWE5YjZjOGQwNDc2ODlhY2Q=',
        'Content-Type': 'application/x-www-form-urlencoded',
      },
      data: data,
    };
    const rest = await axios(config);
    console.log(rest);

    const t = rest.data;
    const token = String(t['access_token']);

    console.log(token);
    const text = 'Bearer' + ' ' + token;

    console.log(text);

    if (score < -0.5) {
      var config = {
        method: 'get',
        url:
          'https://api.spotify.com/v1/search?q=positive&type=playlist&limit=3',
        headers: {
          Authorization: text,
        },
      };
      const resp2 = await axios(config);
      console.log(resp2.data);

      return res.status(200).json({
        success: true,
        sentiment_data: sentiment_data.data,
        playlist: resp2.data,
      });
    } else if (-0.5 < score && score < 0.5) {
      var config = {
        method: 'get',
        url: 'https://api.spotify.com/v1/search?q=soul&type=playlist&limit=3',
        headers: {
          Authorization: text,
        },
      };
      const resp2 = await axios(config);
      console.log(resp2.data);

      return res.status(200).json({
        success: true,
        sentiment_data: sentiment_data.data,
        playlist: resp2.data,
      });
    } else {
      var config = {
        method: 'get',
        url: 'https://api.spotify.com/v1/search?q=party&type=playlist&limit=3',
        headers: {
          Authorization: text,
        },
      };
      const resp2 = await axios(config);
      console.log(resp2.data);

      return res.status(200).json({
        success: true,
        sentiment_data: sentiment_data.data,
        playlist: resp2.data,
      });
    }
  } catch (e) {
    return res.status(500).json({
      success: false,
      message: 'Something went Wrong',
    });
  }
});

module.exports = router;