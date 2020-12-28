import React, { useState, useEffect } from 'react';
import Avatar from '@material-ui/core/Avatar';
import Button from '@material-ui/core/Button';
import CssBaseline from '@material-ui/core/CssBaseline';
import TextField from '@material-ui/core/TextField';
import LocalHospitalIcon from '@material-ui/icons/LocalHospital';
import Typography from '@material-ui/core/Typography';
import { makeStyles } from '@material-ui/core/styles';
import Container from '@material-ui/core/Container';
import Card from '@material-ui/core/Card';
import CardContent from '@material-ui/core/CardContent';
import Link from '@material-ui/core/Link';
import axios from 'axios'
import { useHistory } from 'react-router-dom'
import Snackbar from '@material-ui/core/Snackbar';
import MuiAlert from '@material-ui/lab/Alert';

function Alert(props) {
  return <MuiAlert elevation={6} variant="filled" {...props} />;
}

const useStyles = makeStyles((theme) => ({
  root: {
    marginTop: theme.spacing(8),
  },
  paper: {
    display: 'flex',
    flexDirection: 'column',
    alignItems: 'center',
  },
  avatar: {
    margin: theme.spacing(1),
    backgroundColor: theme.palette.secondary.main,
  },
  form: {
    width: '100%',
    marginTop: theme.spacing(1),
  },
  submit: {
    margin: theme.spacing(3, 0, 2),
  },
}));

export default function Register() {

  const [user, setUser] = useState({
    name: '',
    email: '',
    password: '',
    specialization: ''
  })

  const [alert,setAlert]=useState(false);
  const [alert1,setAlert1]=useState(false);

  const [isAuthenticated, setIsAuthenticated] = useState(false)

  let history = useHistory()
  useEffect(() => {
    if (isAuthenticated) {
      history.push('/home')
    }
    // eslint-disable-next-line
  }, [isAuthenticated])

  const { name, email, password, specialization } = user;
  const onChange = e => setUser({ ...user, [e.target.name]: e.target.value })

  const onSubmit = async e => {
    e.preventDefault();
    if (email === '' || password === '' || name==='') {
      // M.toast({ html: `Please fill in all fields to login successfully!` });
      setAlert(true);
    }
    else {
      var data = JSON.stringify({ name, email, password, Specialization: specialization });
      try {
        var config = {
          method: 'post',
          url: 'https://health-care-auto.herokuapp.com/api/doctor/register',
          headers: {
            'Content-Type': 'application/json'
          },
          data: data
        };
        const response = await axios(config)
        localStorage.setItem('userId', response.data.doctor._id)
        localStorage.setItem('username', response.data.doctor.name)
        localStorage.setItem('token', response.data.token)

        console.log(response.data)
        if (response) {
          setIsAuthenticated(true)
        }
      } catch (error) {
        //Something wentr wrong 
        setAlert1(true)
      }
    }

  }

  const handleClose = (event, reason) => {
    if (reason === 'clickaway') {
      return;
    }

    setAlert(false);
  };

  const handleClose1 = (event, reason) => {
    if (reason === 'clickaway') {
      return;
    }

    setAlert1(false);
  };

  const classes = useStyles();

  return (
    <Container component="main" maxWidth="sm">
      <CssBaseline />
      <Card className={classes.root}>
        <CardContent className={classes.paper}>
          <Avatar className={classes.avatar}>
            <LocalHospitalIcon />
          </Avatar>
          <Typography component="h1" variant="h5">
            Register
        </Typography>
          <form className={classes.form} noValidate>
            <TextField
              variant="outlined"
              margin="normal"
              required
              fullWidth
              id="name"
              label="Name"
              name="name"
              value={name}
              onChange={onChange}
            />
            <TextField
              variant="outlined"
              margin="normal"
              required
              fullWidth
              id="email"
              label="Email Address"
              name="email"
              autoComplete="email"
              value={email}
              onChange={onChange}
            />
            <TextField
              variant="outlined"
              margin="normal"
              required
              fullWidth
              name="password"
              label="Password"
              type="password"
              id="password"
              autoComplete="current-password"
              value={password}
              onChange={onChange}
            />
            <TextField
              variant="outlined"
              margin="normal"
              fullWidth
              name="specialization"
              label="Specialization"
              id="specialization"
              value={specialization}
              onChange={onChange}
            />
            <Button
              type="submit"
              fullWidth
              variant="contained"
              color="primary"
              className={classes.submit}
              onClick={onSubmit}
              disableElevation
            >
              Register
          </Button>
          </form>
          <Link href="/login" variant="body2">
            {"Already have an account? LogIn"}
          </Link>
        </CardContent>
      </Card>
      <Snackbar open={alert} autoHideDuration={6000} onClose={handleClose}>
        <Alert onClose={handleClose} severity="error">
          Please fill in all the required details!
        </Alert>
      </Snackbar>
      <Snackbar open={alert1} autoHideDuration={6000} onClose={handleClose1}>
        <Alert onClose={handleClose1} severity="error">
          Something Went Wrong!
        </Alert>
      </Snackbar>
    </Container>
  );
}
