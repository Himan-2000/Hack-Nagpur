import React, { useEffect, useState } from 'react'
import axios from 'axios'
import { makeStyles } from '@material-ui/core/styles';
import Typography from '@material-ui/core/Typography';
import Paper from '@material-ui/core/Paper';
import Container from '@material-ui/core/Container';
import Card from '@material-ui/core/Card';
import CardContent from '@material-ui/core/CardContent';
import Grid from '@material-ui/core/Grid';
import CardActionArea from '@material-ui/core/CardActionArea';
import CardActions from '@material-ui/core/CardActions';
import Button from '@material-ui/core/Button';

const useStyles = makeStyles((theme) => ({
    root: {
      flexGrow: 1,
      backgroundColor: theme.palette.background.paper,
      marginTop: "100px",
      marginLeft: "280px",
      marginRight: "20px"
    },
    card:{
        margin:"20px"
    }
  }));

const UserDiaryDetails = () => {
    const classes = useStyles()
    const [list, setList] = useState([])

    const getAllUserDiary = async () => {
        try {
            var config = {
                method: 'get',
                url: 'https://health-care-auto.herokuapp.com/api/diary/doc/clientDetail'
            };

            const response = await axios(config)

            console.log(response.data)

            setList(response.data)

        } catch (error) {
            console.log(error)
        }
    }

    useEffect(() => {
        getAllUserDiary()
    }, [])
    console.log(list)

    const displayDiary = () => {
        return list.map((item)=>{
            return(
        <Card className={classes.card} elevation={0} variant="outlined">
            <CardContent>
            <Typography variant="h6" style={{"fontWeight":"bold","marginTop":"20px"}}>{item.user.name}</Typography>
            <Typography variant="h6">{item.Summary}</Typography>
        {/* <Typography variant="body1">{item.text}</Typography> */}
        <Typography>
                      {item.ent_list.map((ent) => {
                        return (
                          <span>{ent} </span>
                        );
                      })}
                    </Typography>
                    <br />
                    <Typography variant="body2">
                    <span style={{"fontWeight":"bold"}}>Positive</span> - {item.scores.pos}
              </Typography>
              <Typography variant="body2">
              <span style={{"fontWeight":"bold"}}>Negative</span> - {item.scores.neg}
              </Typography>
              <Typography variant="body2">
              <span style={{"fontWeight":"bold"}}>Neutral</span> - {item.scores.neu}
              </Typography>
              <Typography variant="body2">
              <span style={{"fontWeight":"bold"}}>OverAll</span> - {item.scores.compound}
              </Typography>
              <br />
        <Typography variant="caption" color="primary">{item.date.slice(0,10)}</Typography></CardContent>
        </Card>
            );
        })
    }

    return (
        <Paper className={classes.root} elevation={0} variant="outlined">
           
            {displayDiary()}
        </Paper>
    )
}

export default UserDiaryDetails
