import 'fontsource-roboto';
import { MuiThemeProvider, createMuiTheme } from '@material-ui/core';
import Diary from './Components/diary';
import Pay from './Components/pay';
import { BrowserRouter as Router, Switch, Route } from "react-router-dom";

const theme = createMuiTheme({
  palette: {
    primary: {
      main: '#64b5f6',
      contrastText: '#fff'
    },

  },
});

function App() {
  return (
    <div className="App">
      <MuiThemeProvider theme={theme}>
        <Router>
          <Switch>
            <Route exact path="/diary/:userId" component={Diary} />
            <Route exact path="/payment" component={Pay} />
          </Switch>
        </Router>
      </MuiThemeProvider>
    </div>
  );
}

export default App;
