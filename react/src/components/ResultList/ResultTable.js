import React from 'react';

import { ResultRow } from './ResultRow';
import './styles.scss';

/*
A component that creates a table with 3 rows, the table is initialised with the header names, and a list of data to be displayed.
To use with different data, change the TH to fit the data and create adjust the ResultRow component acordingly.
*/

export const ResultTable = ({ reslist }) => (
  <table className="ResultTable">
    <thead>
      <tr>
        <th width="5%">#</th>
        <th width="30%">Tittel</th>
        <th width="11%">Pris</th>
        <th width="12%">side </th>
        <th width="20%">placering </th>
        <th width="22%">Sidste opdateret</th>
      </tr>
    </thead>
    <tbody>
      {reslist.map((res, index) => {
        return <ResultRow key={`ResultRow-${index}`} index={index} obj={res} />;
      })}
    </tbody>
  </table>
);
