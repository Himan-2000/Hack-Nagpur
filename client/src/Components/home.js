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
import Divider from '@material-ui/core/Divider';
import Doc from './images/doc.jpg';
import CardMedia from '@material-ui/core/CardMedia';
let i = 0;
const useStyles = makeStyles((theme) => ({
  root: {
    flexGrow: 1,
    backgroundColor: theme.palette.background.paper,
    marginTop: "100px",
    marginLeft: "280px",
    marginRight: "20px",
    padding: "40px"
  },
  root1: {
    flexGrow: 1,
    backgroundColor: theme.palette.background.paper,
    marginTop: "10px",
    marginLeft: "280px",
    marginRight: "20px",
    padding: "40px"
  },
}));

function News() {
  const classes = useStyles();
  const [data, setData] = useState([]);

  useEffect(() => {
    axios
      .get('http://newsapi.org/v2/top-headlines?country=in&category=health&apiKey=eff73011dc944f4a8d5b9cf50640eaa3')
      .then(async (json) => {
        let mydata = json.data;
        console.log('test');
        console.log(mydata);
        setData(mydata.articles.slice(0, 9));
      });
  }, []);

  const displayNews = () => {
    return data.map((news) => {
      return (
        <Grid item xs={4}>
          <Card className={classes.card} variant="outlined">
            <CardActionArea>
              <CardMedia
                component="img"
                alt="Headline"
                height="150"
                image={news.urlToImage}
              />
              <CardContent style={{ "minHeight": "100px" }}>
                <Typography>{news.title}</Typography>
              </CardContent>
            </CardActionArea>
            <CardActions>
              <Button size="small" color="primary"
                onClick={() => { window.open(`${news.url}`) }}>
                View More
      </Button>
            </CardActions>
          </Card>
        </Grid>
      );
    })
  }
  return (
    <React.Fragment>
      <Grid container spacing={5}>
        {displayNews()}
      </Grid>
    </React.Fragment>
  );
}

export default function Home() {
  const classes = useStyles();
  const [data, setData] = useState([]);

  useEffect(() => {
    axios
      .get('https://health-care-auto.herokuapp.com/api/doctor/doc/getDoctor', {
        headers: {
          "X-Auth-Token": localStorage.getItem("token"),
          "content-type": "application/json"
        }

      })
      .then(async (json) => {
        let mydata = json.data;
        console.log('test');
        console.log(mydata);
        setData(mydata);
      });
  }, []);

  return (
    <React.Fragment>
      <Paper className={classes.root} elevation={0} variant="outlined">
        <Avatar src={Doc} style={{ "width": "100px", "height": "90px" }} /><br />
        <Typography variant="body1">Welcome,</Typography>
        <Typography variant="h5" style={{ "fontWeight": "bold" }}>Mr. {data.name}</Typography><Divider /><br />
        <Typography variant="h6">Details</Typography>
        <Typography variant="body1">
          <span style={{ "fontWeight": "bold" }}>Email: </span>{data.email}<br />
          <span style={{ "fontWeight": "bold" }}>Specialization: </span>{data.Specialization && data.Specialization[0]}<br />
        </Typography>
      </Paper>
      <Paper className={classes.root1} elevation={0} variant="outlined">
        <Typography variant="h5" style={{ "fontWeight": "bold" }}>Medical Headlines</Typography><Divider /><br />
        <News />
      </Paper>
    </React.Fragment>
  );
}
