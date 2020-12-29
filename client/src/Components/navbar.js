import React from 'react';
import { makeStyles } from '@material-ui/core/styles';
import Drawer from '@material-ui/core/Drawer';
import AppBar from '@material-ui/core/AppBar';
import CssBaseline from '@material-ui/core/CssBaseline';
import Toolbar from '@material-ui/core/Toolbar';
import List from '@material-ui/core/List';
import Typography from '@material-ui/core/Typography';
import Divider from '@material-ui/core/Divider';
import ListItem from '@material-ui/core/ListItem';
import ListItemIcon from '@material-ui/core/ListItemIcon';
import ListItemText from '@material-ui/core/ListItemText';
import InboxIcon from '@material-ui/icons/MoveToInbox';
import MailIcon from '@material-ui/icons/Mail';
import BorderColorIcon from '@material-ui/icons/BorderColor';
import HomeIcon from '@material-ui/icons/Home';
import { NavLink } from 'react-router-dom';
import PeopleIcon from '@material-ui/icons/People';
import BookIcon from '@material-ui/icons/Book';
import PowerSettingsNewIcon from '@material-ui/icons/PowerSettingsNew';
import HighlightOffIcon from '@material-ui/icons/HighlightOff';


const drawerWidth = 240;

const useStyles = makeStyles((theme) => ({
  root: {
    display: 'flex',
  },
  appBar: {
    zIndex: theme.zIndex.drawer + 1,
  },
  drawer: {
    width: drawerWidth,
    flexShrink: 0,
  },
  drawerPaper: {
    width: drawerWidth,
  },
  drawerContainer: {
    overflow: 'auto',
  },
  content: {
    flexGrow: 1,
    padding: theme.spacing(3),
  },
  link: {
    textDecoration: 'none',
    color: 'black',
  },
}));

export default function Navbar() {
  const classes = useStyles();

  return (
    <React.Fragment className={classes.root}>
      <CssBaseline />
      <AppBar position="fixed" className={classes.appBar}>
        <Toolbar>
          <Typography variant="h6" style={{ fontWeight: "bold" }} noWrap>
            Health Care
          </Typography>
        </Toolbar>
      </AppBar>
      <Drawer
        className={classes.drawer}
        variant="permanent"
        classes={{
          paper: classes.drawerPaper,
        }}
      >
        <Toolbar />
        <div className={classes.drawerContainer}>
          <List component="nav" aria-label="main mailbox folders">
            <NavLink className={classes.link} to='/home'>
              <ListItem button>
                <ListItemIcon>
                  <HomeIcon />
                </ListItemIcon>
                <ListItemText>Home</ListItemText>
              </ListItem>
            </NavLink>
            <NavLink className={classes.link} to='/userpresc'>
              <ListItem button>
                <ListItemIcon>
                  <PeopleIcon />
                </ListItemIcon>
                <ListItemText>User Details</ListItemText>
              </ListItem>
            </NavLink>
            <NavLink className={classes.link} to='/userDiaryDetails'>
              <ListItem button>
                <ListItemIcon>
                  <BookIcon />
                </ListItemIcon>
                <ListItemText>User Diary</ListItemText>
              </ListItem>
            </NavLink>
            <NavLink className={classes.link} to='/home'>
              <ListItem button onClick={() => {
                localStorage.removeItem("currentPatient");
                localStorage.removeItem("patientsNo")
              }}>
                <ListItemIcon>
                  <HighlightOffIcon />
                </ListItemIcon>
                <ListItemText>Clear User</ListItemText>
              </ListItem>
            </NavLink>
            <NavLink className={classes.link} to='/login'>
              <ListItem button onClick={() => { localStorage.clear() }}>
                <ListItemIcon>
                  <PowerSettingsNewIcon />
                </ListItemIcon>
                <ListItemText>Log Out</ListItemText>
              </ListItem>
            </NavLink>

          </List>
        </div>
      </Drawer>
    </React.Fragment>
  );
}