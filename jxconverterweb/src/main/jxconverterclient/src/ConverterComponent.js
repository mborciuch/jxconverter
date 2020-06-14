import React, { Component } from 'react'

class ConverterComponent extends Component {

    constructor(props) {
        super (props);
    }

    render() {
        return(
                <div id={this.props.data.id} className="container-element converter"
                     onChange={this.props.onChange}>
                    <h2>{this.props.data.title}</h2>
                    <textarea id={this.props.data.textareaId} rows="40" cols="60">
                    </textarea>
                </div>
        )
    }
}

export default ConverterComponent;