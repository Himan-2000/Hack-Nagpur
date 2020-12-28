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
import { Paper } from '@material-ui/core';
import axios from 'axios'

const useStyles = makeStyles((theme) => ({
  root: {
    marginTop: "50px",
    marginLeft: "50px",
    marginRight: "50px",
    padding: "50px"
  },
  heading: {
    fontWeight: "bold"
  },
  text: {
    marginTop: "10px"
  },
  submit: {
    marginTop: "20px"
  }
}));

export default function Dairy() {
  const classes = useStyles();

  const [thoughts, setThoughts] = useState();

  const onChange = (e) => setThoughts(e.target.value)

  const onClickHandler = async () => {
    try {
      console.log(thoughts)
      var data = JSON.stringify({ "query": thoughts });

      var config = {
        method: 'post',
        url: 'https://healthcare-sentiment.herokuapp.com/fetch',
        headers: {
          'Content-Type': 'application/json'
        },
        data: data
      };

      const response = await axios(config)

      console.log(response.data)

    } catch (error) {

    }
  }

  return (
    <React.Fragment>
      <Paper className={classes.root} elevation={0} variant="outlined" >
        <Typography className={classes.heading} variant="h5" color="primary">Personal Diary</Typography>
        <TextField
          className={classes.text}
          label=""
          required
          variant="outlined"
          multiline
          rows={23}
          fullWidth
          value={thoughts}
          onChange={onChange}
          placeholder="Something To Share?" />
        <Button
          disableElevation
          type="submit"
          variant="contained"
          color="primary"
          onClick={onClickHandler}
          className={classes.submit}
        >
          Submit
          </Button>
      </Paper>
    </React.Fragment>
  );
}


