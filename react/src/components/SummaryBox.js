import React from 'react'

/* 
Summary box, Component to be added to the top of result list component.
its a list, with list in them, for each element. The idea is to style it, so the list goes horizontal, and sublists goes vertical. first item is the name, second is the value.
There are 2 class names, mostly to try it out. But also because they may require individual styling.

*/

class SummaryBox extends React.Component {
    constructor(props) {
        super(props);
    }
    render() {
        <div className="SB">
            <h1>Søgning for {this.props.query}</h1>
            <ul>
                <ul className={["SI", "mean"]}>
                    <li className="FI">Mean</li>
                    <li className="SecI"></li>
                </ul>
                <ul className={["SI", "mode"]}>
                    <li className="FI">mode</li>
                    <li className="SecI"></li>
                </ul>
                <ul className={["SI", ""]}>
                    <li className="FI">Results found</li>
                    <li className="SecI"></li>
                </ul>
            </ul>

        </div>
    }

}