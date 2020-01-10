import React, { useState } from 'react';
import DescriptionBox from './DescriptionBox';

export const ResultRow = ({ obj, index }) => {
  const [isExpanded, setExpanded] = useState(false);

  const { title, price, origin, location, lastupdate, productId } = obj;

  return (
    <>
      <tr
        key={productId}
        onClick={() => setExpanded(isExpanded => !isExpanded)}
        className="ResultRow__normalRow"
      >
        <td className="count">#{index + 1}</td>
        <td className="title">{title}</td>
        <td className="price">{price}</td>
        <td className="origin">{origin}</td>
        <td className="location">{location}</td>
        <td className="update">{lastupdate}</td>
      </tr>
      {/* {isExpanded && ( */}
      <tr className={`ResultRow__expandedRow${isExpanded ? '' : 'hide'}`}>
        <td colSpan="6">
          <DescriptionBox obj={obj} />
        </td>
      </tr>
      {/* )} */}
    </>
  );
};
