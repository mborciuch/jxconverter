import React, {Component} from 'react';
import './App.css';

class App extends Component {
    render() {
        return (
            <div>
                <div className="header">
                    <h1>JSON to XML & XML to JSON Converter</h1>
                </div>
                <div className="description">
                    <p>Put your data below</p>
                </div>
                <div className="converters-container">
                    <div id="xml-to-json--converter" className="container-element converter">
                        <h2>xml</h2>
                        <textarea id="xml-input-field">

                        </textarea>
                    </div>
                    <div className="container-element buttons">
                        <p>
                            <button className="button"> --V</button>
                            <button className="button"> V--</button>
                        </p>
                    </div>
                    <div id="json-to-xml-converter" className="container-element converter">
                        <h2>json</h2>
                        <textarea id="json-input-field">

                        </textarea>
                    </div>
                </div>
            </div>
        );
    }
}


export default App;
