import React from 'react';
import facade from '../../apiFacade';
import { ResultTable } from './ResultTable';

export class ResultList extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      reslist: [
        {
          productId: 1,
          origin: 'DBA',
          title: 'Test Item nr 2. This is a test item.',
          url: 'https://www.dba.dk/samlekort-magic-the-gather/id-1061586087/',
          location: '8800 Viborg',
          price: '50 kr.',
          imgurl: 'https://i.ebayimg.com/images/g/WSgAAOSwJeVd4OJd/s-l200.jpg',
          lastupdate: '29/11/2019 14:33:50',
          description:
            'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Curabitur sed ullamcorper nulla. Pellentesque quis sapien justo. Etiam pellentesque ornare eros, ullamcorper mattis libero facilisis eget. Aenean et ante ut sapien.',
        },
        {
          productId: 2,
          origin: 'DBA',
          title: 'Test Item nr 1. This is a test item.',
          url: 'https://www.dba.dk/hanky-panky-tryllesaet/id-113677663/',
          location: 'Coop.dk Shopping - Store besparelser',
          price: '170 kr.',
          imgurl: 'https://i.ebayimg.com/images/g/3yYAAOSwOb9d3~k0/s-l200.jpg',
          lastupdate: '29/11/2019 14:33:50',
          description:
            'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Curabitur sed ullamcorper nulla. Pellentesque quis sapien justo. Etiam pellentesque ornare eros, ullamcorper mattis libero facilisis eget. Aenean et ante ut sapien.',
        },
      ],
    };
  }

  getDescription() {}

  exampleAdd(e) {
    e.preventDefault();
    const { reslist } = this.state;
    const { key } = this.state;
    var list = this.state.reslist;
    const newItem = this.newItem.value;

    list.unshift({
      key: this.state.key,
      title: newItem,
      origin: 'button',
      lastupdate: 'ligeNU!',
      price: 9999,
      imgurl: 'https://i.ebayimg.com/images/g/WSgAAOSwJeVd4OJd/s-l200.jpg',
      description:
        'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Curabitur sed ullamcorper nulla. Pellentesque quis sapien justo. Etiam pellentesque ornare eros, ullamcorper mattis libero facilisis eget. Aenean et ante ut sapien.',
    });

    this.setState({ reslist: list, key: key + 1 });
  }

  getAll(e) {
    e.preventDefault();
    const { reslist } = this.state;
    var list = this.state.reslist;
    facade.TryGet().then(res => this.setState({ reslist: res }));
  }

  somefunction(e) {
    e.preventDefault();
    const { msgs } = this.state;
    var list = this.state.msgs;
    const newItem = this.newItem.value;
    facade.TryGet(newItem, list).then(res => this.setState({ msgs: list }));
  }

  render() {
    const { msgs } = this.state;
    return (
      <div className="ResultList">
        {/* <form
          onSubmit={e => {
            this.exampleAdd(e);
          }}
        >
          <label> SearchBar</label>
          <input
            ref={input => (this.newItem = input)}
            placeholder="SEARCH"
            id="addButton"
          />
          <button type="submit"> Search </button>
        </form>
        <button
          onClick={e => {
            this.getAll(e);
          }}
        >
          Hent Producter fra vores API
        </button> */}

        <div className="ResultTableContainer">
          <ResultTable reslist={this.state.reslist} />
        </div>
      </div>
    );
  }
}
