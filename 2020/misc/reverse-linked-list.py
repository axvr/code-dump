# a -> b -> c -> d -> None
# None <- a <- b <- c <- d
# return d (new_head)

def reverseLinkedList(head):
    prev = None

    while head is not None:
        next_head = head.next
        head.next = prev
        prev = head
        head = next_head

    return prev

def printLinkedList(head):
    while head is not None:
        print(head.value)
        head = head.next

class LinkedList:
    def __init__(self, value, next = None):
        self.value = value
        self.next = next

lst = LinkedList("a", LinkedList("b", LinkedList("c", LinkedList("d"))))
print("Original")
printLinkedList(lst)
print("Reversed")
printLinkedList(reverseLinkedList(lst))

