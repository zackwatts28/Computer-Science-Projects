import React, {Component } from 'react';
import logo from './logo.svg';
import './Navigation.css';

class Navigation extends Component {
  render() {
    const sections = ['Home', 'About', 'Services', 'Portfolio', 'Contact'];
    const navLinks = sections.map( section => {
      return(
        <li><a href={'#' + section}>{section}</a></li>
      )
    });
    return (
      <nav>
        <h2 className="Logo">{this.props.logoTitle}</h2>      
        <ul>
          {navLinks} 
        </ul>
      </nav>

    );
  }
}

export default Navigation;