const Pusher = require('pusher')
const config = require('config');
const key = config.get('pusherKey');
const secret = config.get('pusherSecret')

const pusher = new Pusher({
    appId: '1126019',
    key,
    secret,
    cluster: 'ap2',
    useTLS: true
});

module.exports = pusher;