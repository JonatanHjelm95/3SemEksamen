import React from 'react';
import { FiSearch } from 'react-icons/fi';
import { BrowserRouter as Router, NavLink } from 'react-router-dom';
import Login from './Login';
import { ResultList } from './ResultList/ResultList';
import { words } from '../global/suggestion-words';
import './styles.scss';

class Navigation extends React.Component {
  constructor(props) {
    super(props);

    this.state = {
      suggestions: [],
    };

    this.searchSuggestionsRef = React.createRef();
  }

  componentDidMount() {
    document.addEventListener('keydown', this.pressedEscape, false);
    document.addEventListener('mousedown', this.clickOutside);
  }

  componentWillUnmount() {
    document.removeEventListener('keydown', this.pressedEscape, false);
    document.removeEventListener('mousedown', this.clickOutside);
  }

  exampleAdd(e) {
    e.preventDefault();
  }

  search = e => {
    e.persist();

    const searchedWord = e.target.value.trim();

    if (searchedWord.trim().length == 0) {
      this.setState({ suggestions: [] });
      return;
    }

    const newSuggestions = words.filter(word => word.startsWith(searchedWord));
    this.setState({ suggestions: newSuggestions });
  };

  clickOutside = e => {
    if (
      this.searchSuggestionsRef.current != null &&
      !this.searchSuggestionsRef.current.contains(e.target)
    ) {
      this.setState({ suggestions: [] });
    }
  };

  pressedEscape = e => {
    if (e.keyCode === 27) {
      this.setState({ suggestions: [] });
    }
  };

  clickSuggestionItem = item => {
    alert('Clicked: ' + item);
  };

  render() {
    const { suggestions } = this.state;
    return (
      <div>
        <Router>
          <div className="Header">
            <div className="container-buttons">
              <NavLink className="link" to="/" exact>
                <span>HOME</span>
              </NavLink>

              <NavLink className="link" to="/contact">
                <span>LIVE LOG</span>
              </NavLink>
            </div>
            <div className="container-search">
              <form
                className="form"
                onSubmit={e => {
                  this.exampleAdd(e);
                }}
              >
                <FiSearch className="search-icon" />
                <input
                  ref={input => (this.newItem = input)}
                  placeholder="Search here..."
                  id="addButton"
                  className="search"
                  onChange={this.search}
                />
                {suggestions.length > 0 && (
                  <div
                    className="search-suggestion"
                    ref={this.searchSuggestionsRef}
                  >
                    {suggestions.map(suggestion => (
                      <div
                        className="search-suggestion-item"
                        onClick={() => this.clickSuggestionItem(suggestion)}
                      >
                        {suggestion}
                      </div>
                    ))}
                  </div>
                )}
              </form>
            </div>
            <div className="container-login">
              <Login />
            </div>
          </div>
          {/* <Switch>
            <Route path="/" exact component={Home} />
            <Route path="/Contact" component={Contact} />
          </Switch> */}
        </Router>

      </div>
    );
  }
}

export default Navigation;
