import React, { useState, useEffect } from 'react';
import Button from '@material-ui/core/Button';
import CssBaseline from '@material-ui/core/CssBaseline';
import TextField from '@material-ui/core/TextField';
import FormControlLabel from '@material-ui/core/FormControlLabel';
import Checkbox from '@material-ui/core/Checkbox';
import Typography from '@material-ui/core/Typography';
import { makeStyles } from '@material-ui/core/styles';
import Container from '@material-ui/core/Container';
import Card from '@material-ui/core/Card';
import CardContent from '@material-ui/core/CardContent';
import Paper from '@material-ui/core/Paper';
import Divider from '@material-ui/core/Divider';
import jsPDF from 'jspdf'
import Img from './images/bg.jpg'
import firebase from './firebase'
import axios from 'axios'
import Snackbar from '@material-ui/core/Snackbar';
import MuiAlert from '@material-ui/lab/Alert';

function Alert(props) {
    return <MuiAlert elevation={6} variant="filled" {...props} />;
}

const useStyles = makeStyles((theme) => ({
    root: {
        marginTop: "100px",
        marginLeft: "200px",
        marginBottom: '100px',
        height: "900px",
        display: 'flex',
        flexDirection: 'column',
        alignItems: 'center',
    },
    form: {
        marginTop: "50px",
        alignItems: "center"

    }
}));

export default function Prescribe(props) {
    const classes = useStyles();
    const [info, setInfo] = useState({
        title: '',
        analysis: '',
        advice: '',
        allergies: '',
        medication: ''
    });
    const [alert, setAlert] = useState(false);

    const [currentUser, setCurrentUser] = useState()

    useEffect(() => {
        const currentuser_channel = props.pusher.subscribe('user');
        currentuser_channel.bind(`${localStorage.getItem('userId')}`, function (data) {
            setCurrentUser(data)
            console.log(data)
            localStorage.setItem('currentPatient', data._id)
            localStorage.setItem('patientsNo', data.contact)
        })

        return () => {
            props.pusher.unsubscribe('user')
        }
    }, [])

    const getUserdetails = async () => {
        var data = JSON.stringify({ "contact": localStorage.getItem('patientsNo') });

        var config = {
            method: 'post',
            url: 'https://health-care-auto.herokuapp.com/api/user/findUser',
            headers: {
                'Content-Type': 'application/json'
            },
            data: data
        };
        const response = await axios(config)
        console.log(response.data)
        setCurrentUser(response.data.user)
    }

    useEffect(() => {
        if (localStorage.getItem('patientsNo')) {
            getUserdetails()
        }
    }, [])

    const { analysis, advice, allergies, medication, title } = info

    const onChange = e => setInfo({ ...info, [e.target.name]: e.target.value })

    const handleClose = (event, reason) => {
        if (reason === 'clickaway') {
            return;
        }

        setAlert(false);
    };

    const sendToDb = async (dataLink) => {
        try {
            var data = JSON.stringify({
                "prescription":
                {
                    "title": title,
                    "details": dataLink
                },
                "userId": localStorage.getItem('currentPatient')
            });

            var config = {
                method: 'post',
                url: 'https://health-care-auto.herokuapp.com/api/doctor/prescription',
                headers: {
                    'x-auth-token': localStorage.getItem('token'),
                    'Content-Type': 'application/json'
                },
                data: data
            };

            const response = await axios(config)
            console.log(response.data)

            if (response) {
                //toast to show ke data is uploaded
            }

        } catch (error) {
            // toast daal de ider
            setAlert(true);
        }
    }


    const hello = () => {
        console.log('hello')

        let doc = new jsPDF();
        // console.log(doc.output('blob'))

        doc.setFontSize(20)
        doc.text(50, 25, 'Secure Health')
        doc.addImage(Img, 'pdf', 50, 35, 80, 10)
        doc.setFontSize(10)
        doc.text(50, 50, `Patient's Name: ${currentUser.name}`)
        doc.text(50, 55, `Age: ${currentUser.age}`)
        doc.text(50, 60, `Doctor's Report`)
        doc.text(50, 65, `Analysis: ${analysis}`)
        doc.text(50, 70, `Suggestions: ${advice}`)
        doc.text(50, 75, `Allergies: ${allergies}`)
        doc.text(50, 80, `Suggested Medications ${medication}`)
        doc.text(50, 90, `${localStorage.getItem('username')}`)
        doc.text(50, 95, `Your Doctor`)
        const hell = doc.save('prescription.pdf')
        console.log(hell.output('blob'))
        const sen = hell.output('blob')
        var d = new Date();
        var n = d.getTime();
        var storageRef = firebase.storage().ref('/' + n);
        storageRef.put(sen).then(function (snapshot) {
            console.log('Uploaded a blob or file!');
            return snapshot.ref.getDownloadURL()
        }).then((dataPDF => {
            console.log(dataPDF)
            sendToDb(dataPDF)
        }))
    }


    return (
        <Container>
            {currentUser && <Paper className={classes.root}>
                <Typography variant="h5" style={{ "textAlign": "center", "paddingTop": "50px", "fontWeight": "bold" }}>
                    Prescription For Mr. {currentUser.name}
                </Typography>
                <Divider />
                <form className={classes.form} noValidate autoComplete="off">
                    <TextField multiline='true' onChange={onChange} variant='outlined' rows={1} id="standard-basic" name="title" value={title} label="Title" style={{ "width": "800px" }} /><br /><br />
                    <TextField multiline='true' onChange={onChange} variant='outlined' rows={5} id="standard-basic" name="analysis" value={analysis} label="Report Analysis" style={{ "width": "800px" }} /><br /><br />
                    <TextField multiline='true' onChange={onChange} variant='outlined' rows={5} id="standard-basic" name="advice" value={advice} label="Special Advise" style={{ "width": "800px" }} /><br /><br />
                    <TextField multiline='true' onChange={onChange} variant='outlined' rows={2} id="standard-basic" name="allergies" value={allergies} label="Allergies" style={{ "width": "800px" }} /><br /><br />
                    <TextField multiline='true' onChange={onChange} variant='outlined' rows={8} id="standard-basic" name="medication" value={medication} label="Medicines" style={{ "width": "800px" }} /><br /><br />
                    <Button
                        // type="submit"
                        fullWidth
                        variant="contained"
                        color="primary"
                        // className={classes.submit}
                        onClick={hello}
                    >
                        Submit
          </Button>
                </form>
            </Paper>}

        </Container>
    );
}