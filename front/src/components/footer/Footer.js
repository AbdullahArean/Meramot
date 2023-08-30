import React from 'react';
import "./footer.css";
import FacebookIcon from '@mui/icons-material/Facebook';
import TwitterIcon from '@mui/icons-material/Twitter';
import { Instagram } from '@mui/icons-material';

function Footer() {
    return (
        <footer>
            <div className="container">
                <div className="row">
                    <div className="contact">
                        <h3>Contact Us</h3>
                        <ul>
                            <li>Email: example@example.com</li>
                            <li>Phone: 555-555-5555</li>
                            <li>Address: 123 Main St, Anytown USA</li>
                        </ul>
                    </div>
                    <div className="follow-us">
                        <h3>Follow Us</h3>
                        <ul>
                            <li><a href="#"><FacebookIcon /> Facebook</a></li>
                            <li><a href="#"><TwitterIcon /> Twitter</a></li>
                            <li><a href="#"><Instagram /> Instagram</a></li>
                        </ul>
                    </div>
                </div>
                <div className="copyrights">
                    <p> © 2023 Meramot Inc, All Rights Reserved.</p>
                </div>
            </div>
        </footer>
    );
}

export default Footer;
