import React, {Component} from 'react';
import './App.css';

class App extends Component {

    constructor(props) {
        super(props);
        this.props = {url: "/converterts"};
        this.state = {
            xmlValue: "",
            jsonValue: ""
        }

    }

    handleXmlTextAreaChange(e) {
        e.preventDefault();
        this.setState(
            {xmlValue: e.target.value}
        )
    }

    handleConvertToJson(e) {
        fetch(this.props.url, {
            method: "POST",
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(this.state.xmlValue)
        })
            .then(response => response.json())
            .then(jsonValue => this.setState({jsonValue}));
    }

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
                        <textarea id="xml-input-field" rows="40" cols="60">

                        </textarea>
                    </div>
                    <div className="container-element buttons">
                        <p>
                            <button className="button"> To Json</button>
                            <button className="button"> To Xml</button>
                        </p>
                    </div>
                    <div id="json-to-xml-converter" className="container-element converter">
                        <h2>json</h2>
                        <textarea id="json-input-field" rows="40" cols="60">

                        </textarea>
                    </div>
                </div>
            </div>
        );
    }
}


export default App;
