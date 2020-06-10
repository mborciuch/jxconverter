import React, {Component} from 'react';
import './App.css';

const url = "/api/converters";

class App extends Component {

    constructor(props) {
        super(props);
        this.state = {
            xmlValue: "",
            jsonValue: ""
        };
        this.handleXmlTextAreaChange = this.handleXmlTextAreaChange.bind(this);
        this.handleConvertToJson = this.handleConvertToJson.bind(this);

    }

    handleXmlTextAreaChange(e) {
        e.preventDefault();
        this.setState(
            {xmlValue: e.target.value}
        )
    }

    updateJsonTextArea(value){
        document.getElementById("json-input-field").value = value;
    }

    handleConvertToJson() {
        fetch(`${url}?conversionType=toJson`, {
            method: "POST",
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({value: this.state.xmlValue})
        })
            .then(response => response.json())
            .then(jsonValue => {
                    this.setState({jsonValue : JSON.stringify(jsonValue.value)});
                    this.updateJsonTextArea(JSON.stringify(jsonValue.value));
                }
                )

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
                    <div id="xml-to-json--converter" className="container-element converter"
                         onChange={this.handleXmlTextAreaChange}>
                        <h2>xml</h2>
                        <textarea id="xml-input-field" rows="40" cols="60">

                        </textarea>
                    </div>
                    <div className="container-element buttons">
                        <p>
                            <button className="button" onClick={this.handleConvertToJson}> To Json</button>
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
