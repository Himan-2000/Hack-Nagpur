import React,{ useState, useEffect } from 'react';
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
import Divider from '@material-ui/core/Divider';
import Doc from './images/doc.jpg';

const useStyles = makeStyles((theme) => ({
  root: {
    flexGrow: 1,
    backgroundColor: theme.palette.background.paper,
    marginTop:"100px",
    marginLeft:"280px",
    marginRight:"20px",
    padding:"40px"
  },
}));

export default function Home() {
  const classes = useStyles();
  const [data, setData] = useState([]);
  useEffect(() => {
    axios
      .get('https://health-care-auto.herokuapp.com/api/doctor/doc/getDoctor')
      .then(async (json) => {
        let mydata = json.data;
        console.log('test');
        console.log(mydata);
        setData(mydata);
      });
  }, []);

  return (
    <Paper className={classes.root} elevation={0} variant="outlined">
        <Avatar src={Doc} style={{"width":"100px","height":"90px"}} /><br />
        <Typography variant="body1">Welcome,</Typography>
        <Typography variant="h5" style={{"fontWeight":"bold"}}>DR. MAHESH MEHTA</Typography><Divider /><br />
        <Typography variant="h6">Details</Typography>
        <Typography variant="body1">
            <span style={{"fontWeight":"bold"}}>Email:</span>mehta@gmail.com<br />
            <span style={{"fontWeight":"bold"}}>Specialiization:</span>Optician<br />
        </Typography>
    </Paper>
  );
}
