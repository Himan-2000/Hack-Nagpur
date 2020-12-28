import firebase from 'firebase';

const config = {
    apiKey: "AIzaSyCyW-8oMgA4IQGUTz6Yd_pxSuf9ayjRwJ0",
    authDomain: "codeit-d447b.firebaseapp.com",
    databaseURL: "https://codeit-d447b.firebaseio.com",
    projectId: "codeit-d447b",
    storageBucket: "codeit-d447b.appspot.com",
    messagingSenderId: "118908509251",
    appId: "1:118908509251:web:d8fab20d8bea436ae348f6",
    measurementId: "G-QLWK4HCLSX"
}

firebase.initializeApp(config);
export default firebase