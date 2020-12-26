import React, { useState, useEffect } from 'react';
import Avatar from '@material-ui/core/Avatar';
import Button from '@material-ui/core/Button';
import CssBaseline from '@material-ui/core/CssBaseline';
import TextField from '@material-ui/core/TextField';
import FormControlLabel from '@material-ui/core/FormControlLabel';
import Checkbox from '@material-ui/core/Checkbox';
import LocalHospitalIcon from '@material-ui/icons/LocalHospital';
import Typography from '@material-ui/core/Typography';
import { makeStyles } from '@material-ui/core/styles';
import Container from '@material-ui/core/Container';
import Card from '@material-ui/core/Card';
import CardContent from '@material-ui/core/CardContent';
import Link from '@material-ui/core/Link';
import axios from 'axios'
import { useHistory } from 'react-router-dom'

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

export default function LogIn() {
  const [user, setUser] = useState({
    email: '',
    password: ''
  })

  const [isAuthenticated, setIsAuthenticated] = useState(false)
  let history = useHistory()
  useEffect(() => {
    if (isAuthenticated) {
      history.push('/prescribe')
    }
    // eslint-disable-next-line
  }, [isAuthenticated])


  const { email, password } = user;
  const onChange = e => setUser({ ...user, [e.target.name]: e.target.value })

  const onSubmit = async e => {
    e.preventDefault();
    if (email === '' || password === '') {
      // M.toast({ html: `Please fill in all fields to login successfully!` });
    }
    else {
      var data = JSON.stringify({ email, password });

      var config = {
        method: 'post',
        url: 'https://health-care-auto.herokuapp.com/api/doctor/login',
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
      setIsAuthenticated(true)
    }

  }
  const classes = useStyles();

  return (
    <Container component="main" maxWidth="xs">
      <CssBaseline />
      <Card className={classes.root}>
        <CardContent className={classes.paper}>
          <Avatar className={classes.avatar}>
            <LocalHospitalIcon />
          </Avatar>
          <Typography component="h1" variant="h5">
            Log In
        </Typography>
          <form className={classes.form} noValidate>
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
              autoFocus
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
              value={password}
              onChange={onChange}
              autoComplete="current-password"
            />
            <FormControlLabel
              control={<Checkbox value="remember" color="primary" />}
              label="Remember me"
            />
            <Button
              type="submit"
              fullWidth
              variant="contained"
              color="primary"
              onClick={onSubmit}
              className={classes.submit}
            >
              Log In
          </Button>
          </form>
          <Link href="/register" variant="body2">
            {"Don't have an account? Register"}
          </Link>
        </CardContent>
      </Card>
    </Container>
  );
}