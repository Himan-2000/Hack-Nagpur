import './App.css';
import 'fontsource-roboto';
import { MuiThemeProvider, createMuiTheme } from '@material-ui/core';
import ContainedButtons from './Components/temp';
import Navbar from './Components/navbar';
import LogIn from './Components/login(doctor)';
import Prescribe from './Components/presc(doctor)';
import Register from './Components/register(doctor)';
import Userpresc from './Components/userpresc';
import { BrowserRouter as Router, Switch, Route } from "react-router-dom";
import Pusher from 'pusher-js'
import react, { useState, useEffect } from 'react'

const theme = createMuiTheme({
  palette: {
    primary: {
      main: '#64b5f6',
      contrastText: '#fff'
    },
  },
});

function App() {

  const [pusher, setPusher] = useState()

  useEffect(() => {
    setPusher(new Pusher('b48cb6e685ac70d70a56', {
      cluster: 'ap2'
    }))
    console.log(1)
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, [])

  return (
    <div className="App">
      {pusher && <MuiThemeProvider theme={theme}>
        <Router>
          <Switch>
            <Route path="/login">
              <LogIn />
            </Route>
            <Route path="/register">
              <Register />
            </Route>
            <Route path="/prescribe" >
              <Navbar />
              <Prescribe pusher={pusher} />
            </Route>
            <Route path="/userpresc">
              <Navbar />
              <Userpresc />
            </Route>
          </Switch>
        </Router>
      </MuiThemeProvider>}
    </div>
  );
}

export default App;
