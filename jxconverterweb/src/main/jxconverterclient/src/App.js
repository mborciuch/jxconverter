import React, {Component} from 'react';
import './App.css';
import './ConverterComponent';
import ConverterComponent from "./ConverterComponent";

const url = "/api/converters";
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
        this.handleConvertToJson = this.handleConvertToJson.bind(this);

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
                    this.setState({jsonValue: JSON.stringify(jsonValue.value)});
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
                    <ConverterComponent data={xmlConverter} onChange={this.handleXmlTextAreaChange}/>
                    <div className="container-element buttons">
                        <p>
                            <button className="button" onClick={this.handleConvertToJson}> To Json</button>
                            <button className="button"> To Xml</button>
                        </p>
                    </div>
                    <ConverterComponent data={jsonConverter} onChange={this.handleJsonTextAreaChange}/>

                </div>
            </div>
        );
    }
}


export default App;
