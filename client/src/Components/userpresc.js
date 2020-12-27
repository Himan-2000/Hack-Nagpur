import React, { useState, useEffect } from 'react';
import PropTypes from 'prop-types';
import { makeStyles } from '@material-ui/core/styles';
import AppBar from '@material-ui/core/AppBar';
import Tabs from '@material-ui/core/Tabs';
import Tab from '@material-ui/core/Tab';
import Typography from '@material-ui/core/Typography';
import Box from '@material-ui/core/Box';
import Paper from '@material-ui/core/Paper';
import Container from '@material-ui/core/Container';
import Card from '@material-ui/core/Card';
import CardContent from '@material-ui/core/CardContent';
import Grid from '@material-ui/core/Grid';
import CardActionArea from '@material-ui/core/CardActionArea';
import CardActions from '@material-ui/core/CardActions';
import Button from '@material-ui/core/Button';
import FileCopyIcon from '@material-ui/icons/FileCopy';
import axios from 'axios';
import Link from '@material-ui/core/Link';
import AccountCircleIcon from '@material-ui/icons/AccountCircle';
import Avatar from '@material-ui/core/Avatar';


function TabPanel(props) {
  const { children, value, index, ...other } = props;

  return (
    <div
      role="tabpanel"
      hidden={value !== index}
      id={`simple-tabpanel-${index}`}
      aria-labelledby={`simple-tab-${index}`}
      {...other}
    >
      {value === index && (
        <Box p={3}>
          <Typography>{children}</Typography>
        </Box>
      )}
    </div>
  );
}

TabPanel.propTypes = {
  children: PropTypes.node,
  index: PropTypes.any.isRequired,
  value: PropTypes.any.isRequired,
};

function a11yProps(index) {
  return {
    id: `simple-tab-${index}`,
    'aria-controls': `simple-tabpanel-${index}`,
  };
}

const useStyles = makeStyles((theme) => ({
  root: {
    flexGrow: 1,
    backgroundColor: theme.palette.background.paper,
    marginTop: "100px",
    marginLeft: "280px",
    marginRight: "20px"
  },
}));

function Prescription(props) {
  const classes = useStyles();
  const [data, setData] = useState([]);
  const [count, setCount] = useState();
  const [name, setName] = useState("");
  const [currentUser, setCurrentUser] = useState()

  useEffect(() => {
    const currentuser_channel = props.hell.pusher.subscribe('user');
    currentuser_channel.bind(`${localStorage.getItem('userId')}`, function (data) {
      setCurrentUser(data)
      console.log(data)
      localStorage.setItem('currentPatient', data._id)
      localStorage.setItem('patientsNo', data.contact)
      getUserdetails()
    })

    return () => {
      props.hell.pusher.unsubscribe('user')
    }
  }, [])

  const getUserdetails = async () => {
    console.log(localStorage.getItem('patientsNo'))
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
    axios
      .get(`https://health-care-auto.herokuapp.com/api/user/${localStorage.getItem('patientsNo')}`)
      .then(async (json) => {
        let mydata = json.data;
        console.log('test');
        console.log(mydata);
        setData(mydata.userPrescriptions);
        setCount(mydata.noOfPrescriptions);
        setName(mydata.user.name);
      });
  }

  useEffect(() => {
    if (localStorage.getItem('patientsNo')) {
      getUserdetails()
    }
  }, [])

  const displayPresc = () => {
    console.log(count);
    return data.map((presc, index) => {
      return (
        <Grid item xs={4}>
          <Card className={classes.card} elevation={0} variant="outlined">
            <CardContent>
              <Typography>
                <span style={{ "fontWeight": "bold" }}>No.</span><span>-{index + 1}</span>
              </Typography>
              <Typography>
                <span style={{ "fontWeight": "bold" }}>Doctor</span><span>-{presc.doctor.name}</span>
              </Typography>
              <Typography>
                <span style={{ "fontWeight": "bold" }}>Specialization</span><span>-{presc.doctor.Specialization}</span>
              </Typography>
              <Typography>
                <span style={{ "fontWeight": "bold" }}>Date</span><span>-{presc.date.slice(0, 10)}</span>
              </Typography>
            </CardContent>
            <CardActions>
              <Button
                variant="contained"
                color="primary"
                className={classes.button}
                endIcon={<FileCopyIcon></FileCopyIcon>}
                onClick={() => { window.open(`${presc.details}`) }}
                disableElevation
              >
                View
      </Button>
            </CardActions>
          </Card>
        </Grid>
      );
    })
  }
  return (
    <React.Fragment>
      <Typography variant="h5" style={{ "fontWeight": "bold", "marginTop": "20px", "marginLeft": "20px" }}>Patient - {name}</Typography><br />
      <Grid container spacing={5}>
        {displayPresc()}
      </Grid>
    </React.Fragment>
  );
}

function Reports(props) {
  const classes = useStyles();
  const [data, setData] = useState([]);
  const [count, setCount] = useState();
  const [name, setName] = useState("");
  const [currentUser, setCurrentUser] = useState()

  useEffect(() => {
    const currentuser_channel = props.hell.pusher.subscribe('user');
    currentuser_channel.bind(`${localStorage.getItem('userId')}`, function (data) {
      setCurrentUser(data)
      console.log(data)
      localStorage.setItem('currentPatient', data._id)
      localStorage.setItem('patientsNo', data.contact)
      getUserdetails()
    })

    return () => {
      props.hell.pusher.unsubscribe('user')
    }
  }, [])

  const getUserdetails = async () => {
    console.log(localStorage.getItem('patientsNo'))
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
    axios
      .get(`https://health-care-auto.herokuapp.com/api/user/${localStorage.getItem('patientsNo')}`)
      .then(async (json) => {
        let mydata = json.data;
        console.log('test');
        console.log(mydata);
        setData(mydata.userPrescriptions);
        setCount(mydata.noOfPrescriptions);
        setName(mydata.user.name);
      });
  }

  useEffect(() => {
    if (localStorage.getItem('patientsNo')) {
      getUserdetails()
    }
  }, [])

  const displayReps = () => {
    console.log(count);
    return data.map((rep, index) => {
      return (
        <Grid item xs={4}>
          <Card className={classes.card} elevation={0} variant="outlined">
            <CardContent>
              <Typography>
                <span style={{ "fontWeight": "bold" }}>No.</span><span>-{index + 1}</span>
              </Typography>
              <Typography>
                <span style={{ "fontWeight": "bold" }}>Title</span><span>-{rep.title}</span>
              </Typography>
              <Typography>
                <span style={{ "fontWeight": "bold" }}>Date</span><span>-{rep.date.slice(0, 10)}</span>
              </Typography>
            </CardContent>
            <CardActions>
              <Button
                variant="contained"
                color="primary"
                className={classes.button}
                endIcon={<FileCopyIcon></FileCopyIcon>}
                onClick={() => { window.open(`${rep.file}`) }}
                disableElevation
              >
                View
    </Button>
            </CardActions>
          </Card>
        </Grid>
      );
    })
  }
  return (
    <React.Fragment>
      <Typography variant="h5" style={{ "fontWeight": "bold", "marginTop": "20px", "marginLeft": "20px" }}>Patient - {name}</Typography><br />
      <Grid container spacing={5}>
        {displayReps()}
      </Grid>
    </React.Fragment>
  );
}

export default function Userpresc(props) {
  const classes = useStyles();
  const [value, setValue] = React.useState(0);

  const [hell, setHell] = useState()

  const handleChange = (event, newValue) => {

    setValue(newValue);
  };

  useEffect(() => {
    setHell(props)
  }, [])

  return (
    <Paper className={classes.root} elevation={0} variant="outlined">
      <Tabs value={value} onChange={handleChange} aria-label="simple tabs example" indicatorColor="primary"
        textColor="primary" style={{ "marginLeft": "40px", "marginTop": "20px" }}>
        <Tab label="Prescriptions" style={{ "fontWeight": "bold" }} {...a11yProps(0)} />
        <Tab label="Reports" style={{ "fontWeight": "bold" }} {...a11yProps(1)} />
      </Tabs>
      <TabPanel value={value} index={0}>
        {hell && <Prescription hell={hell} />}
      </TabPanel>
      <TabPanel value={value} index={1}>
        {hell && <Reports hell={hell} />}
      </TabPanel>
    </Paper>
  );
}
