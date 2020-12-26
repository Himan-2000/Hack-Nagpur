import React from 'react';
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

const useStyles = makeStyles((theme) => ({
    root:{
        marginTop: "100px",
        marginLeft:"200px",
        height:"500px",
        display: 'flex',
        flexDirection: 'column',
        alignItems: 'center',
    },
    form:{
        marginTop:"50px",
        alignItems:"center"

    }
}));

export default function Prescribe() {
  const classes = useStyles();

  return (
    <Container>
        <Paper className={classes.root}>
            <Typography variant="h5" style={{"textAlign":"center","paddingTop":"50px","fontWeight":"bold"}}>
                Prescription For Mr. Heth Gala
            </Typography>
            <Divider />
            <form className={classes.form} noValidate autoComplete="off">
            <TextField id="standard-basic" label="Report Analysis" style={{"width":"800px"}} /><br/><br/>
            <TextField id="standard-basic" label="Special Advise" style={{"width":"800px"}} /><br/><br/>
            <TextField id="standard-basic" label="Allergies" style={{"width":"800px"}} /><br/><br/>
            <TextField id="standard-basic" label="Medicines" style={{"width":"800px"}} /><br/><br/>
            <Button
            type="submit"
            fullWidth
            variant="contained"
            color="primary"
            className={classes.submit}
          >
              Submit
          </Button>
            </form>
            
        </Paper>
    </Container>
  );
}