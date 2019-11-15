import React from 'react';
import {bookService} from '../services/BookService';
import {findDOMNode} from 'react-dom';
import {FormControl, InputLabel, Input, Button, makeStyles} from '@material-ui/core';
import  { Editors }  from 'react-data-grid-addons';
import ReactDataGrid from 'react-data-grid';

const { DropDownEditor } = Editors;

const Categories = [
    { id: "it", value: "IT" },
    { id: "cooking", value: "Cooking" },
    { id: "teen", value: "Teen" }
];
const categoryTypeEditor = <DropDownEditor options={Categories} />;

const columns = [
    {key: 'id', name: 'ID'},
    {key: 'bookName', name: 'Book Name', editable: true},
    {key: 'author', name: 'Author', editable: true},
    {key: 'description', name: 'Description', editable: true},
    {key: 'price', name: 'Price', editable: true},
    {key: 'category', name: 'Category', editor: categoryTypeEditor}];


class BookComponent extends React.Component {
    constructor(props) {
        super(props);
        this.bookTableDom = null;
        this.state = {
            editedBooks: [],
            localBooks: [],
            offSet: 1,
            limit: 20
        };
    }

    componentDidMount() {
        bookService.get("books", {page: this.state.offSet, limit: this.state.limit}).then((result) => {
            this.setState({localBooks: result.data.books});
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
                    offSet: this.state.offSet + 1
                });
            })
        }
    };

    saveBook = () => {
        bookService.put("books/updateList", {listBooks: this.state.editedBooks}).then(() => {
            this.setState({editedBooks: []});
        })
    };

    render() {
        return (
            <div style={{maxWidth: "90%", margin: "auto"}}>
                <div className="searchForm" style={{float: "left",  marginBottom: "15px"}}>
                    <FormControl>
                        <InputLabel htmlFor="my-input">Email address</InputLabel>
                        <Input id="my-input" aria-describedby="my-helper-text"/>
                    </FormControl>
                </div>
                <div className="tableButton" style={{float:"right", marginTop: "15px"}}>

                    <Button variant="outlined" onClick={this.saveBook}>
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
                    minHeight={600}
                />
            </div>
        );
    };
}

export default BookComponent;