import React from 'react';
import {bookService} from '../services/BookService';
import {findDOMNode} from 'react-dom';
import {FormControl, InputLabel, Input, Button} from '@material-ui/core';
import  { Editors }  from 'react-data-grid-addons';
import ReactDataGrid from 'react-data-grid';


class BookComponent extends React.Component {
    constructor(props) {
        super(props);
        this.bookTableDom = null;
        this.state = {
            editedBooks: [],
            localBooks: [],
            offSet: 1,
            limit: 20,
            total: 0
        };
    }
    componentDidMount() {
        bookService.get("books", {page: this.state.offSet, limit: this.state.limit}).then((result) => {
            this.setState({localBooks: result.data.books, total: result.data.count});
        });
        this.bookTableDom = findDOMNode(this).querySelector('.react-grid-Canvas');
        this.bookTableDom.addEventListener('scroll', this.scrollListener);
    };

    onGridRowsUpdated = ({fromRow, toRow, updated}) => {
        this.setState(state => {
            const rows = state.localBooks.slice();
            for (let i = fromRow; i <= toRow; i++) {
                rows[i] = {...rows[i], ...updated};
                this.updateEditedBook(rows[i]);
            }
            return {localBooks: rows};
        });
    };

    updateEditedBook = (book) => {
        let books = this.state.editedBooks.slice();
        const oldBook = books.filter(b => b.id === book.id );
        if(oldBook.length > 0){
            books = books.filter(b => b.id !== oldBook.id);
        }
        books.push(book);
        this.setState({editedBooks: books})
    };

    scrollListener = () => {
        if (
            this.bookTableDom.scrollHeight === (this.bookTableDom.scrollTop + this.bookTableDom.clientHeight)) {
            bookService.get("books", {page: this.state.offSet + 1, limit: this.state.limit}).then((result) => {
                this.setState({
                    localBooks: [...this.state.localBooks, ...result.data.books],
                    offSet: this.state.offSet + 1,
                    total: result.data.count
                });
            })
        }
    };

    saveBook = () => {
        bookService.post("books/updateList", {listBooks: this.state.editedBooks}).then((response) => {
            if(response && response.status === 200) {
                alert("Save successfully");
                this.setState({editedBooks: []});
            } else {
                alert("Save unsuccessfully");
                throw response;
            }
        })
    };

    sortRows = (initialRows, sortColumn, sortDirection) => {
        const comparer = (a, b) => {
            if (sortDirection === "ASC") {
                return a[sortColumn] > b[sortColumn] ? 1 : -1;
            } else if (sortDirection === "DESC") {
                return a[sortColumn] < b[sortColumn] ? 1 : -1;
            }
        };
        if(sortDirection !== "NONE") {
            const books = this.state.localBooks.slice();
            this.setState({localBooks: books.sort(comparer)})
        }
    };

    render() {
        const { DropDownEditor } = Editors;

        const Categories = [
            { id: "it", value: "IT" },
            { id: "cooking", value: "Cooking" },
            { id: "teen", value: "Teen" }
        ];
        const categoryTypeEditor = <DropDownEditor options={Categories} />;

        const columns = [
            {key: 'id', name: 'ID', sortable : true},
            {key: 'bookName', name: 'Book Name', editable: true, sortable : true},
            {key: 'author', name: 'Author', editable: true, sortable : true},
            {key: 'description', name: 'Description', editable: true, sortable : true},
            {key: 'price', name: 'Price', editable: true, sortable : true},
            {key: 'category', name: 'Category', editor: categoryTypeEditor, sortable : true}];

        return (
            <div style={{maxWidth: "90%", margin: "auto"}}>
                <div className="searchForm" style={{float: "left",  marginBottom: "15px"}}>
                    <FormControl>
                        <InputLabel htmlFor="my-input">Email address</InputLabel>
                        <Input id="my-input" aria-describedby="my-helper-text"/>
                    </FormControl>
                </div>
                <div className="tableButton" style={{float:"right", marginTop: "15px"}}>

                    <Button variant="outlined" onClick={this.saveBook} style={{marginRight: "5px"}}>
                        Save
                    </Button>
                    <Button variant="outlined">
                        Delete
                    </Button>
                </div>
                <ReactDataGrid
                    columns={columns}
                    rowGetter={i => this.state.localBooks[i]}
                    rowsCount={this.state.localBooks.length}
                    enableCellSelect={true}
                    onGridRowsUpdated={this.onGridRowsUpdated}
                    minHeight={500}
                    onGridSort={(sortColumn, sortDirection) =>
                        this.sortRows(this.state.localBooks, sortColumn, sortDirection)
                    }
                />
                <div  style={{float: "right",  marginBottom: "15px"}}>
                    Showing {this.state.localBooks.length} of {this.state.total} item
                </div>
            </div>
        );
    };
}

export default BookComponent;