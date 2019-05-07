import React, { Component } from 'react';
import './Services.css';

class Services extends Component{
    render() {
        return (
            <div className="services">
                <h3>Services</h3>
                <h2>What I offer</h2>
                <div className="row">
                    <div>
                        <span>
                            <i class="fas fa-book"></i>
                        </span>
                        <h4>Experience</h4>
                        <p>Familiar with Angular and React</p>
                    </div>
                    <div>
                        <span>
                            <i class="fas fa-phone"></i>
                        </span>
                        <h4>Contact me</h4>
                        <p>Find my number on my resume</p>
                    </div>
                    <div>
                        <span>
                            <i class="fas fa-school"></i>
                        </span>
                        <h4>UNC Wilmington</h4>
                        <p>B.S. in Computer Science</p>
                    </div>
                    <div>
                        <span>
                            <i class="fas fa-home"></i>
                        </span>
                        <h4>Local boy</h4>
                        <p>Grew up in South Charlotte</p>
                    </div>
                </div>
            </div>
        );
    }



}

export default Services; 