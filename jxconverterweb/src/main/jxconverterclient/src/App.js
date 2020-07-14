import React, {Component} from 'react';
import './App.css';
import './ConverterComponent';
import ConverterComponent from "./ConverterComponent";

const url = "/converters";
const xmlConverter = {
    id: "xml-to-json-converter",
    title: "xml",
    textareaId: "xml-input-field"
};
const jsonConverter = {
    id: "json-to-xml-converter",
    title: "json",
    textareaId: "json-input-field"
};

class App extends Component {

    constructor(props) {
        super(props);
        this.state = {
            xmlValue: "",
            jsonValue: ""
        };
        this.handleXmlTextAreaChange = this.handleXmlTextAreaChange.bind(this);
        this.handleJsonTextAreaChange = this.handleJsonTextAreaChange.bind(this);
        this.handleConvertToJson = this.handleConvertToJson.bind(this);
        this.handleConvertToXml = this.handleConvertToXml.bind(this);
    }

    handleXmlTextAreaChange(e) {
        e.preventDefault();
        this.setState(
            {xmlValue: e.target.value}
        )
    }

    handleJsonTextAreaChange(e) {
        e.preventDefault();
        this.setState(
            {jsonValue: e.target.value}
        )
    }

    updateJsonTextArea(value) {
        document.getElementById("json-input-field").value = value;
    }

    updateXmlTextArea(value) {
        document.getElementById("xml-input-field").value = value;
    }

    handleConvertToJson() {
        document.getElementById(jsonConverter.textareaId).value = "";
        fetch(`${url}?conversionType=toJson`, {
            method: "POST",
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({value: this.state.xmlValue})
        })
            .then(response => {
                return response.json()
            })
            .then(jsonValue => {
                    if (jsonValue.value !== undefined) {
                        this.setState({jsonValue: JSON.stringify(jsonValue.value)});
                    } else {
                        this.setState({jsonValue: JSON.stringify(jsonValue.message)});
                    }
                    this.updateJsonTextArea(this.state.jsonValue);
                }
            );
    }
    handleConvertToXml() {
        document.getElementById(xmlConverter.textareaId).value = "";
        fetch(`${url}?conversionType=toXml`, {
            method: "POST",
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({value: this.state.jsonValue})
        })
            .then(response => response.json())
            .then(xmlValue => {
                    if (xmlValue.value !== undefined) {
                        let value = JSON.stringify(xmlValue.value);
                        value = value.substring(1, value.length - 1);
                        this.setState({xmlValue: value});
                    } else {
                        this.setState({xmlValue: JSON.stringify(xmlValue.message)});
                    }
                    this.updateXmlTextArea(this.state.xmlValue);
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
                    <ConverterComponent data={xmlConverter} onChange={this.handleXmlTextAreaChange}/>
                    <div className="container-element buttons">
                        <p>
                            <button className="button" onClick={this.handleConvertToJson}> To Json</button>
                            <button className="button" onClick={this.handleConvertToXml}> To Xml</button>
                        </p>
                    </div>
                    <ConverterComponent data={jsonConverter} onChange={this.handleJsonTextAreaChange}/>
                </div>
            </div>
        );
    }
}


export default App;
