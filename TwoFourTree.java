public class TwoFourTree {
    private class TwoFourTreeItem {
        int values = 1;             //num of values in that item/node
        int value1 = 0;                             // always exists.
        int value2 = 0;                             // exists iff the node is a 3-node or 4-node.
        int value3 = 0;                             // exists iff the node is a 4-node.
        boolean isLeaf = true;
        
        TwoFourTreeItem parent = null;              // parent exists iff the node is not root.
        TwoFourTreeItem leftChild = null;           // left and right child exist iff the note is a non-leaf.
        TwoFourTreeItem rightChild = null;          
        TwoFourTreeItem centerChild = null;         // center child exists iff the node is a non-leaf 3-node.
        TwoFourTreeItem centerLeftChild = null;     // center-left and center-right children exist iff the node is a non-leaf 4-node.
        TwoFourTreeItem centerRightChild = null;

        // a 2-node has one data element, and if internal has two child nodes;
        public boolean isTwoNode() 
        {
            if(values == 1) 
            {
                if(leftChild != null && rightChild != null)
                {
                    return true;
                }
                else if(isLeaf && value1 != 0)
                    return true;
            }
                return false;
        }

        // a 3-node has two data elements, and if internal has three child nodes
        public boolean isThreeNode() 
        {
            if(values == 2)
            {
                if(leftChild != null && rightChild != null && centerChild != null)
               {
                    return true;
               }
               else if(isLeaf)
                return true;
            }
                return false;
        }

        //a 4-node has three data elements, and if internal has four child nodes;
        public boolean isFourNode() 
        {
            if(values  == 3)
            {
                if(leftChild != null && rightChild != null && centerLeftChild != null && centerRightChild != null)
                {
                    return true;
                }
                else if(isLeaf)
                    return true;
            }
            return false;
        }

        //returns if node is a root or not
        public boolean isRoot() 
        {
            if(parent == null)
                return true;

            return false;
        }


        //creates a 2-node
        public TwoFourTreeItem(int value1) 
        {
            this.value1 = value1;

        }

        //creats a 3-node
        public TwoFourTreeItem(int value1, int value2) 
        {
            this.values = 2;
            this.value1 = value1;
            this.value2 = value2;
            
        }

        //creates a 4-node
        public TwoFourTreeItem(int value1, int value2, int value3) 
        {
            this.values = 3;
            this.value1 = value1;
            this.value2 = value2;
            this.value3 = value3;
            
        }

        //splits 4-node 
        public void split(TwoFourTreeItem head)
        {
             int midVal = value2;
                if(head.parent == null)
                {
                    if(head.isLeaf == true)
                   {
                    TwoFourTreeItem newRoot = new TwoFourTreeItem(midVal);
                    TwoFourTreeItem leftChild = new TwoFourTreeItem(value1);
                    TwoFourTreeItem rightChild = new TwoFourTreeItem(value3);

                    newRoot.leftChild = leftChild;
                    leftChild.parent = newRoot;
                    newRoot.rightChild = rightChild;
                    rightChild.parent = newRoot;
                    newRoot.isLeaf = false;
                    head = newRoot;
                    root = head;
                   }
                   else 
                   {
                        TwoFourTreeItem newRoot = new TwoFourTreeItem(midVal);
                        TwoFourTreeItem newLeft = new TwoFourTreeItem(value1);
                        TwoFourTreeItem newRight = new TwoFourTreeItem(value3);
                        //Set the new left to carry the previous children from the left & centerleft, update isLeaf to false
                        newLeft.leftChild = head.leftChild;
                        head.leftChild = null;
                        newLeft.rightChild = head.centerLeftChild;
                        head.centerLeftChild = null;
                        newLeft.leftChild.parent = newLeft;
                        newLeft.rightChild.parent = newLeft;
                        newLeft.parent = newRoot;


                        //Set the new left to carry the previous children from the left & centerleft, update isLeaf to false
                        newRight.leftChild = head.centerRightChild;
                        head.centerRightChild = null;
                        newRight.rightChild = head.rightChild;
                        head.rightChild = null;
                        newRight.rightChild.parent = newRight;
                        newRight.leftChild.parent = newRight;
                        newRight.parent = newRoot;


                        newRoot.leftChild = newLeft;
                        newRoot.leftChild.isLeaf = false;
                        newRoot.rightChild = newRight;
                        newRoot.rightChild.isLeaf = false;
                        newRoot.isLeaf = false;
                        head = newRoot;
                        root = head;
                    }

                    
    
                }
                else if(head.parent.isTwoNode())
                {
                    if(midVal < head.parent.value1)
                    {
                        head.parent.value2 = head.parent.value1;
                        head.parent.value1 = midVal;
                        head.parent.values = 2;
                        TwoFourTreeItem newLeft = new TwoFourTreeItem(value1);
                        TwoFourTreeItem newCenter = new TwoFourTreeItem(value3);
                        head.parent.leftChild = newLeft;
                        head.parent.centerChild = newCenter;
                        head.parent.isLeaf = false;
                    }

                    else if(midVal > head.parent.value1)
                    {
                        head.parent.value2 = midVal;
                        head.parent.values = 2;
                        TwoFourTreeItem newCenter = new TwoFourTreeItem(value1);
                        TwoFourTreeItem newRight = new TwoFourTreeItem(value3);
                        newCenter.parent = head.parent;
                        newCenter.leftChild = head.leftChild;
                        newCenter.rightChild = head.centerLeftChild;
                        head.parent.centerChild = newCenter;

                        newRight.parent = head.parent;
                        newRight.rightChild = head.rightChild;
                        newRight.leftChild = head.centerRightChild;
                        head.parent.rightChild = newRight;
                        head.parent.isLeaf = false;
                        if(head.leftChild != null)
                        {
                            newCenter.leftChild.parent = newCenter;
                            newCenter.rightChild.parent = newCenter;
                            newCenter.isLeaf = false;
                        }
                        if(head.rightChild != null)
                        {
                            newRight.rightChild.parent = newRight;
                            newRight.isLeaf = false;
                        }
                        if(head.centerRightChild != null)
                        {
                            newRight.leftChild.parent = newRight;
                            newRight.isLeaf = false;
                        }
                        head = parent.rightChild;
                    }

                }

                //if parent is a 3-node (has 2 values)
                else if(head.parent.isThreeNode())
                {
                    midVal = value2;
                    //if mid value is less than parent.value 1 than it's the left side
                    if(midVal < head.parent.value1)
                    {
                        head.parent.value3 = head.parent.value2;
                        head.parent.value2 = head.parent.value1;
                        head.parent.value1 = midVal;
                        head.parent.values = 3;
                        TwoFourTreeItem newLeft = new TwoFourTreeItem(value1);
                        TwoFourTreeItem newCenterLeft = new TwoFourTreeItem(value3);
                        head.centerChild = newCenterLeft;
                        head.rightChild = newLeft;
                        head.parent.isLeaf = false;
                    }

                    else if(midVal > head.parent.value1 && midVal < head.parent.value2)
                    {
                        head.parent.value2 = head.parent.value3;
                        head.parent.value2 = midVal;
                        head.parent.values = 3;
                        TwoFourTreeItem newCenterLeft = new TwoFourTreeItem(head.value1);
                        TwoFourTreeItem newCenterRight = new TwoFourTreeItem(head.value3);
                        head.parent.value3 = head.parent.value2;
                        head.parent.value2 = midVal;
                        head.parent.centerLeftChild = newCenterLeft;
                        head.parent.centerRightChild = newCenterRight;
                        head = parent;
                    
                    }

                    //if mid value is more than parent.value2 than it's the right side
                    else if(midVal > head.parent.value2)
                    {
                        head.parent.value3 = midVal;
                        TwoFourTreeItem newCenterLeft = head.parent.centerChild;
                        TwoFourTreeItem newCenterRight = new TwoFourTreeItem(head.value1);
                        TwoFourTreeItem newRight = new TwoFourTreeItem(value3);
                        head.parent.values = 3;
                        head.parent.centerChild = null;
                        newCenterLeft.parent = head.parent;
                        if(head.centerLeftChild != null)
                        {
                            newCenterRight.leftChild = head.leftChild;
                            newCenterRight.rightChild = head.centerLeftChild;
                            newCenterRight.leftChild.parent = newCenterRight;
                            newCenterRight.rightChild.parent = newCenterRight;
                            newCenterRight.isLeaf = false;

                        }

                        if(head.centerRightChild!= null)
                        {
                            newRight.leftChild = head.centerRightChild;
                            newRight.leftChild.parent = newRight;
                            newRight.rightChild = head.rightChild;
                            newRight.rightChild.parent = newRight;
                            newRight.isLeaf = false;
                            
                        }
                        newCenterRight.parent = head.parent;
                        newRight.parent = head.parent;
                        head.parent.centerRightChild = newCenterRight;
                        head.parent.rightChild = newRight;
                        head.parent.centerLeftChild = newCenterLeft;
                        parent.isLeaf = false;
                        head = parent;
 
                    }
               }

        }

        public TwoFourTreeItem find(int value)
        {
            if(value == value1 || value == value2 || value == value3)
                return this;
            
            //if root is a 2-node, check left or right
            if(isTwoNode())
            {
                if(leftChild == null)
                    return null;
                //if value is less than, check the left
                else if(value < value1)
                {
                    return leftChild.find(value);
                }
                 
                if(rightChild == null)
                    return null;
                //if value is greater than, check the right
                else if(value > value1)
                {
                    return rightChild.find(value);
                }
                            
            }
            //if root is a 3-node, check left, center or right
            if(isThreeNode())
            {
                if(leftChild == null)
                    return null;

                else if(value < value1)
                {
                    //check through left subtree
                    return leftChild.find(value);
                }
                
                else if(centerChild== null)
                    return null;

                else if(value > value1 && value < value2)
                {
                    //check through center subtree
                    return centerChild.find(value);
                }
                else if(rightChild == null)
                    return null;

                else if(value > value2)
                {
                //search through the right subtree
                return rightChild.find(value);
                }
            }
            
            //if root is a 4-node, check left, center left, center right or right
            if(isFourNode())
            {  
                //if value is smaller than value1
                if(value < value1)
                {
                    //search through left subtree
                    return leftChild.find(value);
                }
        
                else if(centerLeftChild == null)
                    return null;
                //if value is greater than value but smaller than value2
                else if(value > value1 && value < value2)
                {
                    //search through center left subtree
                    return centerLeftChild.find(value);
                }
        
                else if(centerRightChild == null)
                    return null;
                //if value is greater than value2 but smaller than value3
                else if(value > value2 && value < value3)
                {
                    //search through center right subtree
                    return centerRightChild.find(value);
                }
        
                else if(rightChild == null)
                    return null;
                //if value is greater than value3
                else if(value > value3)
                {
                    //search through right subtree
                    return rightChild.find(value);
                }
        
        
            }
            return null;
  
        }
                
        public TwoFourTreeItem addToRoot(int value)
        {
            if(parent == null)
            {
                if(isTwoNode())
                {
                    if(value < value1)
                    {
                        TwoFourTreeItem newNode = new TwoFourTreeItem(value, value1);
                        
                        root = newNode;
                    }
                    else if(value > value1)
                    {
                        TwoFourTreeItem newNode = new TwoFourTreeItem(value1, value);
                        root = newNode;
                    }
                }

                else if(root.isThreeNode())
                {
                    if(value < root.value1)
                    {
                        TwoFourTreeItem newNode = new TwoFourTreeItem(value, root.value1, root.value2);
                        root = newNode;
                    }
                    else if(value > root.value1 && value < root.value2)
                    {
                        TwoFourTreeItem newNode = new TwoFourTreeItem(root.value1, value, root.value2);
                        root = newNode;
                    }
                    else if(value > root.value2)
                    {
                        TwoFourTreeItem newNode = new TwoFourTreeItem(root.value1, root.value2, value);
                        root = newNode;
                    }
                }
            }
            return root;
        }

        public TwoFourTreeItem add(int value)
        {
            //if value is at the root, return root
            if(value == value1 || value == value2 || value  == value3)
                return this;
           
            //Adding value 2 & value 3 to the root
            if(parent == null && !isFourNode() && isLeaf == true)
            {
                return addToRoot(value);
            }
            //if current node is a 4-node, split 
            else if(isFourNode())
            {
                
                split(this);

                //if root is a 4node
                if(parent == null)
                {
                    if(root.isTwoNode())
                    {   
                        //After spliting, go left if value is less than 
                        if(value < root.value1)
                            return root.leftChild.add(value);

                        //or go right if value is greater than
                        else if(value > root.value1)
                            return root.rightChild.add(value);
                    }
                }    
                
                //if newly split node is a 2-node
                else if(parent.isTwoNode())
                {   
                    //After spliting, go left if value is less than 
                    if(value < parent.value1)
                        return parent.leftChild.add(value);

                    //or go right if value is greater than
                    else 
                        return parent.rightChild.add(value);
                        
                }

                //If newly split node is a 3-node
                else if(parent.isThreeNode())
                {
                    if(value < parent.value1)
                        return parent.leftChild.add(value);

                    else if(value > parent.value1 && value < value3)
                        return parent.centerChild.add(value);

                    else if(value > parent.value3)
                        return parent.rightChild.add(value);
                }

                //If newly split node is a 3-node
                else if(parent.isFourNode())
                {
                    if(value < parent.value1)
                        return parent.leftChild.add(value);
                    
                    else if(value > parent.value1 && value < parent.value2)
                        return parent.centerLeftChild.add(value);
                        
                    else if(value > parent.value1 && value > parent.value2 && value < parent.value3)
                        return parent.centerRightChild.add(value);


                    else if(value > parent.value3)
                        return parent.rightChild.add(value);
                }
                
            }
            
            //if current node is a 2node(has 1 value) 
            else if(isTwoNode())
            {
                //if value is less than current node's value1
                if(value < value1)
                {
                    if(isLeaf == true) //insert into the left 
                    {
                        TwoFourTreeItem newNode = new TwoFourTreeItem(value, value1);
                        leftChild = newNode;
                        leftChild.parent = this.parent;
                        parent.leftChild = leftChild;
                        return leftChild;
                    }
                    //else, recurse into the left subtree
                        return leftChild.add(value);
                }
                
                //if value is greater than the current node's value1
                else if(value > value1)
                {
                    //if leaf, insert into the right
                    if(isLeaf == true)
                    {
                        TwoFourTreeItem newNode = new TwoFourTreeItem(value1, value);
                        rightChild = newNode;
                        rightChild.parent = this.parent;
                        parent.rightChild = rightChild;
                        return rightChild;
                    }
                    //else, recurse into the right subtree 
                    return rightChild.add(value);
                    
                }

            }
    
            else if(isThreeNode())
            {
                if(value < value1)
                {
                    if(isLeaf == true)
                    {
                      TwoFourTreeItem newNode = new TwoFourTreeItem(value, value1, value2);
                       leftChild = newNode;
                       leftChild.parent = this.parent;
                        parent.leftChild = leftChild;
                        return leftChild;
                    }
                    return leftChild.add(value);
                    
                }
                
                else if(value > value1 && value < value2)
                {
                    if(isLeaf == true)
                    {
                        TwoFourTreeItem newNode = new TwoFourTreeItem(value1, value, value2);
                        centerChild = newNode;
                        centerChild.parent = this.parent;
                        parent.centerChild = centerChild;
                        return centerChild;
                    }

                    return centerChild.add(value);
                }

                else if(value > value2)
                {
                    if(isLeaf == true)
                    {
                        TwoFourTreeItem newNode = new TwoFourTreeItem(value1, value2, value);
                        rightChild = newNode;
                        rightChild.parent = this.parent;
                        parent.rightChild = rightChild;
                        return rightChild;
                    }
                    return rightChild.add(value);
                }      
            
            
            }
            return root;
        }


        public TwoFourTreeItem rotate(TwoFourTreeItem head)
        {
            TwoFourTreeItem rightSibling = head.parent.rightChild;
            TwoFourTreeItem centerRightSibling = head.parent.centerRightChild;
            TwoFourTreeItem centerSibling = head.parent.centerChild;
            TwoFourTreeItem leftSibling = head.parent.leftChild;
            TwoFourTreeItem centerLeftSibling = head.parent.centerLeftChild;
            //head is the 2-node, now we have to check for cases where the parent is a 3-node
            if(head.parent.isThreeNode()) //if parent is a 3-node
            {
                //if parent is a 3-node there's 4 directions we can go according to where the 2-node 
                //is located
                /*  if the 2-node is the center child, 
                       it can either go left root center OR right root center
                 *  if the 2-node is the left child
                 *      it has to go center root left
                 *  if the 2-node is the right child
                 *      it has to go center root right
                 */
                //if the 2-node is the center child, 
                //  it can either go left root center OR right root center
                if(head == head.parent.centerChild)
                {
                    if(leftSibling.isThreeNode())
                    {
                        TwoFourTreeItem newNode = new TwoFourTreeItem(head.parent.value1, head.value1);
                        head.parent.value1 = leftSibling.value2;
                        leftSibling.values = 1;
                        leftSibling.value2 = 0;
                        leftSibling.parent = head.parent;
                        newNode.parent = head.parent;
                        head.parent.centerChild = newNode;
                        head.parent.leftChild = leftSibling;
                    
                        return head.parent.centerChild;
                    }
                    else if(leftSibling.isFourNode())
                    {
                        TwoFourTreeItem newNode = new TwoFourTreeItem(head.parent.value1, head.value1);
                        head.parent.value1 = leftSibling.value3;
                        leftSibling.values = 2;
                        leftSibling.value3 = 0;
                        leftSibling.parent = head.parent;
                        newNode.parent = head.parent;
                        head.centerChild = newNode;
                        head.leftChild = leftSibling;
                    
                        return head.centerChild;
                    }

                    else if(rightSibling.isThreeNode())
                    {
                        TwoFourTreeItem newNode = new TwoFourTreeItem(head.value1, head.parent.value2);
                        head.parent.value2 = rightSibling.value1;
                        rightSibling.value1 = rightSibling.value2;
                        rightSibling.values = 1;
                        rightSibling.parent = head.parent;
                        newNode.parent = head.parent;
                        head.centerChild = newNode;
                        head.parent.rightChild = rightSibling;
                    
                        return head.centerChild;
                    }

                    else if(rightSibling.isFourNode())
                    {
                        TwoFourTreeItem newNode = new TwoFourTreeItem(head.value1, head.parent.value2);
                        head.parent.value2 = rightSibling.value1;
                        rightSibling.value1 = rightSibling.value2;
                        rightSibling.value2 = rightSibling.value3;
                        rightSibling.value3 =0;
                        rightSibling.values = 2;
                        rightSibling.parent = head.parent;
                        newNode.parent = head.parent;
                        head.centerChild = newNode;
                        head.parent.rightChild = rightSibling;
                    
                        return head.centerChild;
                    }
                }

                //if the 2-node is the left child
                //      it has to go center root left
                else if(head == head.parent.leftChild)
                {
                    if(centerSibling.isThreeNode())
                    {
                        TwoFourTreeItem newNode = new TwoFourTreeItem(head.value1, head.parent.value1);
                        head.parent.value1 = head.centerChild.value1;
                        centerSibling.value1 = centerSibling.value2;
                        centerSibling.values = 1;
                        centerSibling.parent = head.parent;
                        newNode.parent = head.parent;
                        head.parent.leftChild = newNode;
                        head = newNode;

                        return head;

                    }

                    else if(centerSibling.isFourNode())
                    {
                        TwoFourTreeItem newNode = new TwoFourTreeItem(head.value1, head.parent.value1);
                        head.parent.value1 = head.centerChild.value1;
                        centerSibling.value1 = centerSibling.value2;
                        centerSibling.value2 = centerSibling.value3;
                        centerSibling.value3 = 0;
                        centerSibling.values = 2;
                        centerSibling.parent = head.parent;
                        newNode.parent = head.parent;
                        head.parent.leftChild = newNode;
                        head = newNode;

                        return head;
                    }
                }

                //if the 2-node is the right child
                //      it has to go center root right
                else if(head == head.parent.rightChild)
                {
                    if(centerSibling.isThreeNode())
                    {
                        TwoFourTreeItem newNode = new TwoFourTreeItem(head.parent.value2, head.value1);
                        head.parent.value2 = head.centerChild.value2;
                        centerSibling.value2 = 0;
                        centerSibling.values = 1;
                        centerSibling.parent = head.parent;
                        newNode.parent = head.parent;
                        head.parent.rightChild = newNode;
                        head = newNode;

                        return head;

                    }

                    else if(centerSibling.isFourNode())
                    {
                        TwoFourTreeItem newNode = new TwoFourTreeItem(head.value1, head.parent.value1);
                        head.parent.value2 = head.centerChild.value3;
                        centerSibling.value3 = 0;
                        centerSibling.values = 2;
                        centerSibling.parent = head.parent;
                        newNode.parent = head.parent;
                        head.parent.rightChild = newNode;
                        head = newNode;

                        return head;
                    }
                }
            }

            else if(head.parent.isFourNode())
            {

                if(head == head.parent.centerLeftChild)
                {
                    if(leftSibling.isThreeNode())
                    {
                        TwoFourTreeItem newNode = new TwoFourTreeItem(head.parent.value1, head.value1);
                        head.parent.value1 = head.leftChild.value2;
                        leftSibling.value2 = 0;
                        leftSibling.values = 1;
                        leftSibling.parent = head.parent;
                        newNode.parent = head.parent;
                        head.parent.centerLeftChild = newNode;
                        head = newNode;
                        head.parent.leftChild = leftSibling;

                        return head;
                    }

                    else if(leftSibling.isFourNode())
                    {
                        TwoFourTreeItem newNode = new TwoFourTreeItem(head.parent.value1, head.value1);
                        head.parent.value1 = head.leftChild.value3;
                        leftSibling.value3 = 0;
                        leftSibling.values = 2;
                        leftSibling.parent = head.parent;
                        newNode.parent = head.parent;
                        head.parent.centerLeftChild = newNode;
                        head = newNode;
                        head.parent.leftChild = leftSibling;

                        return head;
                    }

                    else if(centerRightSibling.isThreeNode())
                    {
                        TwoFourTreeItem newNode = new TwoFourTreeItem(head.value1, head.parent.value2);
                        head.parent.value2 = head.centerRightChild.value1;
                        centerRightSibling.value1 = centerRightSibling.value2;
                        centerRightSibling.value2 = 0;
                        centerRightSibling.values = 1;
                        centerRightSibling.parent = head.parent;
                        newNode.parent = head.parent;
                        head.parent.centerLeftChild = newNode;
                        head = newNode;
                        head.parent.centerRightChild = centerRightSibling;

                        return head;

                    }

                    else if(centerRightSibling.isFourNode())
                    {
                        TwoFourTreeItem newNode = new TwoFourTreeItem(head.value1, head.parent.value2);
                        head.parent.value2 = head.centerRightChild.value1;
                        centerRightSibling.value1 = centerRightSibling.value2;
                        centerRightSibling.value2 = centerRightSibling.value3;
                        centerRightSibling.value3 = 0;
                        centerRightSibling.values = 2;
                        centerRightSibling.parent = head.parent;
                        newNode.parent = head.parent;
                        head.parent.centerLeftChild = newNode;
                        head = newNode;
                        head.parent.centerRightChild = centerRightSibling;

                        return head;
                    }

                }

                else if(head == head.parent.centerRightChild)
                {
                    if(centerLeftSibling.isThreeNode())
                    {
                        TwoFourTreeItem newNode = new TwoFourTreeItem(head.parent.value2, head.value1);
                        head.parent.value2 = head.centerLeftChild.value2;
                        centerLeftSibling.value2 = 0;
                        centerLeftSibling.values = 1;
                        centerLeftSibling.parent = head.parent;
                        newNode.parent = head.parent;
                        head.parent.centerRightChild = newNode;
                        head = newNode;
                        head.parent.centerRightChild = centerLeftSibling;

                        return head;

                    }

                    else if(centerLeftSibling.isFourNode())
                    {
                        TwoFourTreeItem newNode = new TwoFourTreeItem(head.parent.value2, head.value1);
                        head.parent.value2 = head.centerLeftChild.value3;
                        centerLeftSibling.value3 = 0;
                        centerLeftSibling.values = 1;
                        centerLeftSibling.parent = head.parent;
                        newNode.parent = head.parent;
                        head.parent.centerRightChild = newNode;
                        head = newNode;
                        head.parent.centerRightChild = centerLeftSibling;

                        return head;
                        
                    }

                    else if(rightSibling.isThreeNode())
                    {
                        TwoFourTreeItem newNode = new TwoFourTreeItem(head.value1, head.parent.value3);
                        head.parent.value3 = head.rightChild.value1;
                        rightSibling.value1 = rightSibling.value2;
                        rightSibling.value2 = 0;
                        rightSibling.values = 1;
                        rightSibling.parent = head.parent;
                        newNode.parent = head.parent;
                        head.parent.centerRightChild = newNode;
                        head = newNode;
                        head.parent.rightChild = rightSibling;

                        return head;
                    }

                    else if(rightSibling.isFourNode())
                    {
                        TwoFourTreeItem newNode = new TwoFourTreeItem(head.value1, head.parent.value3);
                        head.parent.value3 = head.rightChild.value1;
                        rightSibling.value1 = rightSibling.value2;
                        rightSibling.value2 = rightSibling.value3;
                        rightSibling.value3 = 0;
                        rightSibling.values = 2;
                        rightSibling.parent = head.parent;
                        newNode.parent = head.parent;
                        head.parent.centerRightChild = newNode;
                        head = newNode;
                        head.parent.rightChild = rightSibling;

                        return head;
                    }
                }
                //if the 2-node is a left child
                else if(head == head.parent.leftChild)
                {
                    if(centerLeftSibling.isThreeNode())
                    {
                        //take parent value1 & head.value1 & make a new left child
                        TwoFourTreeItem newNode = new TwoFourTreeItem(head.value1, head.parent.value1);
                        //take centerLeft.value1 into the parent
                        head.parent.value1 = centerLeftSibling.value1;
                        centerLeftSibling.value1 = centerLeftSibling.value2;
                        centerLeftSibling.values = 1;
                        centerLeftSibling.parent = head.parent;
                        newNode.parent = head.parent;
                        head.parent.leftChild = newNode;
                        head.parent.centerLeftChild = centerLeftSibling;

                        head = newNode;
                        return head;
                    }

                    else if(centerLeftSibling.isFourNode())
                    {
                        //take parent value1 & head.value1 & make a new left child
                        TwoFourTreeItem newNode = new TwoFourTreeItem(head.value1, head.parent.value1);
                        //take centerLeft.value1 into the parent
                        head.parent.value1 = centerLeftSibling.value1;
                        centerLeftSibling.value1 = centerLeftSibling.value2;
                        centerLeftSibling.value2 = centerLeftSibling.value3;
                        centerLeftSibling.values = 2;
                        centerLeftSibling.parent = head.parent;
                        head.parent.leftChild = newNode;
                        head.parent.centerLeftChild = centerLeftSibling;
                        newNode.parent = head.parent;
                        head = newNode;
                        return head;
                    }
                }

                else if(head == head.parent.rightChild)
                {
                    if(centerRightSibling.isThreeNode())
                    {
                        TwoFourTreeItem newNode = new TwoFourTreeItem(head.parent.value3, head.value1);
                        head.parent.value3 = centerRightSibling.value2;
                        centerRightSibling.value2 = 0;
                        centerRightSibling.values = 1;
                        centerRightSibling.parent = head.parent;
                        newNode.parent = head.parent;
                        head.parent.centerRightChild = centerRightSibling;
                        head.parent.rightChild = newNode;
                        head = newNode;
                        return head;
            

                    }

                    else if(centerRightSibling.isFourNode())
                    {
                        TwoFourTreeItem newNode = new TwoFourTreeItem(head.parent.value3, head.value1);
                        head.parent.value3 = centerRightSibling.value3;
                        centerRightSibling.value3 = 0;
                        centerRightSibling.values = 2;
                        centerRightSibling.parent = head.parent;
                        newNode.parent = head.parent;
                        head.parent.centerRightChild = centerRightSibling;
                        head.parent.rightChild = newNode;
                        head = newNode;
                        return head;
                    }
                }
            }
            return head;
        }

        public TwoFourTreeItem findLeftSibling(TwoFourTreeItem head)
        {
            if(!head.isRoot() && head != null)
            {
                if(head.parent.isTwoNode())
                {
                    if(head == head.parent.rightChild)
                    {
                        if(head.parent.leftChild != null)
                            return head.parent.leftChild;
                        else
                        return null;
                    }
                }

                else if(head.parent.isThreeNode())
                {
                    if(head == head.parent.centerChild)
                    {
                        if(head.parent.leftChild!= null)
                            return head.parent.leftChild;
                        else
                            return null;
                    }

                    else if(head == head.parent.rightChild)
                    {
                        if(head.parent.centerChild!= null)
                            return head.parent.centerChild;
                        else
                            return null;
                    }
                }

                else if(head.parent.isFourNode())
                {
                    if(head == head.parent.centerLeftChild)
                    {
                        if(head.parent.leftChild!= null)
                            return head.parent.leftChild;
                        else
                            return null;
                    }

                    else if(head == head.parent.centerRightChild)
                    {
                        if(head.parent.centerLeftChild!= null)
                            return head.parent.centerLeftChild;
                        else
                            return null;
                    }

                    else if(head == head.parent.rightChild)
                    {
                        if(head.parent.centerRightChild!= null)
                            return head.parent.centerRightChild;
                        else
                            return null;
                    }
                }
            }
            return null;
        }

        public TwoFourTreeItem findRightSibling(TwoFourTreeItem head)
        {
            if(!head.isRoot() && head != null && head.parent.isLeaf == false)
            {

                if(head.parent.isTwoNode())
                {
                    if(head == head.parent.leftChild)
                    {
                        if(head.parent.rightChild != null)
                            return head.parent.rightChild;
                        else
                        return null;
                    }
                }

                else if(head.parent.isThreeNode())
                {
                    if(head == head.parent.leftChild)
                    {
                        if(head.parent.centerChild!= null)
                            return head.parent.centerChild;
                        else
                            return null;
                    }

                    else if(head == head.parent.centerChild)
                    {
                        if(head.parent.rightChild!= null)
                            return head.parent.rightChild;
                        else
                            return null;
                    }
                    else 
                        return null;
                }

                else if(head.parent.isFourNode())
                {
                    if(head == head.parent.leftChild)
                    {
                        if(head.parent.centerLeftChild!= null)
                            return head.parent.centerLeftChild;
                        else
                            return null;
                    }

                    else if(head == head.parent.centerLeftChild)
                    {
                        if(head.parent.centerRightChild!= null)
                            return head.parent.centerRightChild;
                        else
                            return null;
                    }

                    else if(head == head.parent.centerRightChild)
                    {
                        if(head.parent.rightChild!= null)
                            return head.parent.rightChild;
                        else
                            return null;
                    }
                }
            }
            return head;
        }

        //Takes in a value and searchs & deletes that node if present in the 2-3-4 tree
        public TwoFourTreeItem delItem(TwoFourTreeItem head, int value)
        {  
            if(root.isLeaf && root.isTwoNode() && root.value1 == value)
            {
                root = null;
                return root;
            }
            //Search for the node that needs to be deleted
            TwoFourTreeItem target = find(value);
            TwoFourTreeItem leftSibling = findLeftSibling(head);
            TwoFourTreeItem rightSibling = findRightSibling(head);
            TwoFourTreeItem targetLeftSibling = findLeftSibling(target);
            TwoFourTreeItem targetRightSibling = findRightSibling(target);
            //If the root is a 2-node and both its children are 2-nodes, fuse it. The new root is a 4-node with:
            if(root.isTwoNode() && root.leftChild.isTwoNode() && root.rightChild.isTwoNode())
            {
                TwoFourTreeItem newRoot = new TwoFourTreeItem(root.leftChild.value1, root.value1, root.rightChild.value1);
                newRoot.leftChild = root.leftChild.leftChild;
                newRoot.centerLeftChild = root.leftChild.rightChild;
                newRoot.centerRightChild = root.rightChild.leftChild;
                newRoot.rightChild = root.rightChild.rightChild;

                root = newRoot;
                return delItem(root, value);
            }
            //Case 1: if x is in a leaf node
            //If the node is a leaf node, remove the value and decrease the number of values 
            else if(target.isLeaf == true)
            {
                //if the node to be deleted is the root with no children, set the root to null
                if(target == root)
                {
                    //if root only has 1 value & that is the value that needs to be deleted
                    if(target.isTwoNode())
                    {
                        root = null;
                        return root;
                    }

                    //if root is a 3-node, delete the target value and return the new root
                    if(target.isThreeNode())
                    {
                        if(value == target.value1)
                        {
                            root = new TwoFourTreeItem(value2);
                            return root;
                        }
                        
                        if(value == target.value2)
                        {
                            root = new TwoFourTreeItem(value1);
                            return root;
                        }
                    }

                    //if root is a 4-node, delete the target value and return the new root
                    if(target.isFourNode())
                    {
                        if(value == target.value1)
                        {
                            root = new TwoFourTreeItem(value2, value3);
                            return root;
                        }
                        
                        else if(value == target.value2)
                        {
                            root = new TwoFourTreeItem(value1, value3);
                            return root;
                        }

                        else if(value == target.value3)
                        {
                            root = new TwoFourTreeItem(value1, value2);
                        }
                    }
                }

                //Case 1.1: If x is either in a 3-node or 4-node
                else if(target.isThreeNode())
                {
                    if(value == target.value1)
                    {
                        TwoFourTreeItem newNode = new TwoFourTreeItem(target.value2);
                        newNode.parent = target.parent;
                        return newNode;

                    }
                        
                    else if(value == target.value2)
                    {
                        TwoFourTreeItem newNode = new TwoFourTreeItem(target.value1);
                        newNode.parent = target.parent;
                        return newNode;
                    }

                }

                else if(target.isFourNode())
                {
                    if(value == target.value1)
                    {
                        TwoFourTreeItem newNode = new TwoFourTreeItem(target.value2, target.value3);
                        newNode.parent = target.parent;
                        return newNode;
                    }
                    
                    else if(value == target.value2)
                    {
                        TwoFourTreeItem newNode = new TwoFourTreeItem(target.value1, target.value3);
                        newNode.parent = target.parent;
                        return newNode;
                    }

                    else if(value == target.value3)
                    {
                        TwoFourTreeItem newNode = new TwoFourTreeItem(target.value1, target.value2);
                        newNode.parent = target.parent;
                        return newNode;
                    }
                }

                //Case 1.2: If x is in a 2-node, we need to consider a few more cases
                else if(target.isTwoNode()) //(Single value/key)
                {

                    //Case 1: If the node containing x has 3-node or 4-node siblings
                    if(targetLeftSibling != null && targetRightSibling != null)
                    {
                        //If the left sibling is a 3-node/4-node, do a left rotation
                        if(leftSibling.isThreeNode() || leftSibling.isFourNode())
                        {
                            target = rotate(target);
                            return delItem(target, value);
                        }
                        //Else, do the right rotation
                        else if(rightSibling.isThreeNode() || rightSibling.isFourNode())
                        {
                            target = rotate(target);
                            return delItem(target, value);
                        }

                        //You can recursively recall delete after this bc after we do the rotation,
                        // our target node will become a 3-node
                    }


                    //Case 2: If both the siblings are 2-node the parent node is either a 3-node or a 4-node
                    else if(targetLeftSibling.isTwoNode() && targetRightSibling == null)
                        {
                            if(target.parent.isThreeNode())
                            {
                                TwoFourTreeItem mergedNode = new TwoFourTreeItem(target.value1, target.parent.value1, target.value1);
                                target.parent.value1 = target.parent.value2;
                                target.parent.value2 = 0;
                                target.parent.values = 1;
                                mergedNode.parent = target.parent;
                                target = mergedNode;
                                return delItem(target, value);
                            }
                            else if(target.parent.isFourNode())
                            {
                                TwoFourTreeItem mergedNode = new TwoFourTreeItem(target.value1, target.parent.value1, target.value1);
                                target.parent.value1 = target.parent.value2;
                                target.parent.value2 = 0;
                                target.parent.values = 1;
                                mergedNode.parent = target.parent;
                                target = mergedNode;
                                return delItem(target, value);
                            }
                            

                        }

                        else if(targetLeftSibling.isTwoNode() && targetRightSibling.isTwoNode() && target.parent.isThreeNode()
                        || target.parent.isFourNode())
                        {

                        }

                        else if(targetRightSibling.isTwoNode() && targetLeftSibling == null && target.parent.isThreeNode()
                        || target.parent.isFourNode())
                        {

                        }

                    

                    if(target.parent.isThreeNode() || target.parent.isFourNode() )
                    {
                        /* Convert the 2-node into a 4-node using merge
                     * Merge the following 3 nodes:
                     *  1. the current 2-node that has our target value
                     *  2. left/right sibling node (that is also a 2-node)
                     *  3. the parent node of the two nodes (parent must be 3-node OR 4-node)
                     * 
                     * After the merge, we use case 1.1: If x is either in a 3-node or 4-node
                     * to delete x
                     */
                    

                    }

                    //Case 3: If both siblings and the parent node are a 2-node
                    /* In this particular case, the parent node must be a root.
                     * Merge both the siblings and the parent node to make it a 4-node
                     * then delete x
                     */
                    
                    
                }

            } // End of Case 1: If x is in a leaf node

            //Case 2: If x is in an internal node
            else
            {
                 /*
                1. Find the predecessor of the node containing x. The predecessor is always a leaf node.
                    Predecessor is 
                2. Exchange the node containing x with its predecessor node. This moves the node containing x
                    to the leaf node.
                3. Since x is in a leaf node, this is case 1. Use the rules given in case 1 to delete it. 
                */
            }
            

            return target;
        }


        private void printIndents(int indent) {
            for(int i = 0; i < indent; i++) System.out.printf("  ");
        }

        public void printInOrder(int indent) {
            if(!isLeaf) leftChild.printInOrder(indent + 1);
            printIndents(indent);
            System.out.printf("%d\n", value1);
            if(isThreeNode()) {
                if(!isLeaf) centerChild.printInOrder(indent + 1);
                printIndents(indent);
                System.out.printf("%d\n", value2);
            } else if(isFourNode()) {
                if(!isLeaf) centerLeftChild.printInOrder(indent + 1);
                printIndents(indent);
                System.out.printf("%d\n", value2);
                if(!isLeaf) centerRightChild.printInOrder(indent + 1);
                printIndents(indent);
                System.out.printf("%d\n", value3);
            }
            if(!isLeaf) rightChild.printInOrder(indent + 1);
        }

    }
    
    

    TwoFourTreeItem root = null;

    /* The task is to search for the suitable leaf node where the value should be 
    inserted. In this process whenever we get a 4 node we split that such that 
    we do not need to trace back from the leaf to the root.*/
    //returns true if it already exist, returns false if we had to add it
    public boolean addValue(int value) 
    {
        //If root is empty
        if(root == null)
        {
            root = new TwoFourTreeItem(value);
            return false;
        }

       else if(root != null && hasValue(value) == true)
            return true;
        
        else 
        {
            root.add(value);
            return false;
        }
    }

    public boolean hasValue(int value)
    {
        if(root == null)
            return false;

        TwoFourTreeItem temp = root.find(value);

        if(temp != null)
            return true;
        
        return false;
    }



    public boolean deleteValue(int value) {
        //if tree is empty
        if(root == null)
            return false;
        else
            return true;

        
        
    }

    public void printInOrder() {
        if(root != null) root.printInOrder(0);
    }

   public TwoFourTree() 
    {
        root = null;

    }
    
   
}
