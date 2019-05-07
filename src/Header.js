import React, {Component} from 'react';
import './Header.css';
import Background from './img/BACKIMG.JPG';

const myStyles = {
    backgroundImage: `url( ${Background} )`,
    height: '47vh',
    backgroundSize: 'cover'

}

class Header extends Component{ 
    render(){
        return(
            <header style={myStyles}>
                <h1>{this.props.title}</h1>
                <p>A react web site by Zack Watts </p>
                <a href="button">{this.props.button}</a>
            </header>

        );
    }
};

export default Header;
