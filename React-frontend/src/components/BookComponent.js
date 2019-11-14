import React from 'react';
import {bookService} from '../services/BookService';
import {findDOMNode} from 'react-dom';

import ReactDataGrid from 'react-data-grid';


class BookComponent extends React.Component {
    constructor(props) {
        super(props);
        this.canvas = null;
    }

    state = {
        books: [],
        localBooks: [],
        offSet: 1,
        limit: 20,

    };

    componentDidMount() {
        bookService.get("books", {page: this.state.offSet, limit: this.state.limit}).then((result) => {
            this.setState({books: result.data, localBooks: result.data});
        });
        this.canvas = findDOMNode(this).querySelector('.react-grid-Canvas');
        this.canvas.addEventListener('scroll', this.scrollListener);
    };

    onGridRowsUpdated = ({fromRow, toRow, updated}) => {
        this.setState(state => {
            const rows = state.localBooks.slice();
            for (let i = fromRow; i <= toRow; i++) {
                rows[i] = {...rows[i], ...updated};
            }
            return {localBooks: rows};
        });
    };

    scrollListener = () => {
        if (
            this.canvas.scrollHeight === (this.canvas.scrollTop + this.canvas.clientHeight)) {
            bookService.get("books", {page: this.state.offSet+1, limit: this.state.limit}).then((result) => {
                this.setState({books: [...this.state.books, ...result.data], localBooks: [...this.state.books, ...result.data], offSet: this.state.offSet+1});
            })
        }
    };

    render() {
        const columns = [
            {key: 'id', name: 'ID'},
            {key: 'bookName', name: 'bookName', editable: true},
            {key: 'author', name: 'author', editable: true},
            {key: 'description', name: 'description', editable: true},
            {key: 'price', name: 'price', editable: true},
            {key: 'category', name: 'category', editable: true}];
        return (
            <div style={{maxWidth: "90%", margin: "auto"}}>
                <form onSubmit={this.handleSubmit} style={{marginBottom: "15px", marginTop: "30px"}}>
                    <label>
                        Name:
                        <input type="text" value={this.state.value} onChange={this.handleChange}/>
                    </label>
                    <input type="submit" value="Submit"/>
                </form>
                <ReactDataGrid
                    columns={columns}
                    rowGetter={i => this.state.localBooks[i]}
                    rowsCount={this.state.localBooks.length}
                    enableCellSelect={true}
                    onGridRowsUpdated={this.onGridRowsUpdated}
                    minHeight={600}
                />
            </div>
        );
    };
}

export default BookComponent;